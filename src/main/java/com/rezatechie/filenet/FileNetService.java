package com.rezatechie.filenet;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.util.Id;
import com.rezatechie.dto.DocumentTO;

@Service
public class FileNetService {

    @Autowired
    private FNConnection fnConnection;

    public byte[] downloadDocument(String guid) {
        try {
            ObjectStore store = fnConnection.getObjectStore();
            Document document = Factory.Document.fetchInstance(store, new Id(guid), null);

            try (InputStream contentStream = document.accessContentStream(0);
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = contentStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error downloading document with GUID: " + guid, e);
        }
    }

    public void updateDocumentProperties(DocumentTO documentTO) {
        try {
            ObjectStore store = fnConnection.getObjectStore();
            Document document = Factory.Document.fetchInstance(store, new Id(documentTO.getGuid()), null);

            Properties props = document.getProperties();
            Map<String, Object> updates = documentTO.getProperties();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                props.putObjectValue(entry.getKey(), entry.getValue());
            }

            document.save(RefreshMode.REFRESH);
        } catch (Exception e) {
            throw new RuntimeException("Error updating properties for document with GUID: " + documentTO.getGuid(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
	public void updateDocumentContent(String guid, InputStream newContent, String mimeType) {
        try {
            ObjectStore store = fnConnection.getObjectStore();
            Document document = Factory.Document.fetchInstance(store, new Id(guid), null);

            ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
            contentTransfer.setCaptureSource(newContent);
            contentTransfer.set_ContentType(mimeType);

            ContentElementList contentList = Factory.ContentElement.createList();
            contentList.add(contentTransfer);

            document.set_ContentElements(contentList);
            document.save(RefreshMode.REFRESH);
        } catch (Exception e) {
            throw new RuntimeException("Error updating document content for GUID: " + guid, e);
        }
    }

}
