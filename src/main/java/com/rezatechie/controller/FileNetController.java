package com.rezatechie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.rezatechie.dto.DocumentTO;
import com.rezatechie.filenet.FileNetService;

@EnableWebMvc
@RestController
@RequestMapping("/filenet")
public class FileNetController {

    @Autowired
    private FileNetService fileNetService;

    /**
     * Download a document from FileNet using its GUID.
     */
    //ResponseEntity<byte[]> will automatically make your body binary.
    //serverless-java-container will base64 encode it automatically if needed.
    @GetMapping("/download/{guid}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String guid) {
        try {
            byte[] content = fileNetService.downloadDocument(guid);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(guid + ".bin").build());

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Update properties of a FileNet document.
     */
    @PostMapping("/update-properties")
    public ResponseEntity<String> updateDocumentProperties(@RequestBody DocumentTO dto) {
        try {
            fileNetService.updateDocumentProperties(dto);
            return ResponseEntity.ok("Document properties updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating properties: " + e.getMessage());
        }
    }

    /**
     * Update the content of a FileNet document.
     */
    @PostMapping(value = "/update-content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateDocumentContent(
            @RequestPart("guid") String guid,
            @RequestPart("file") MultipartFile file) {

        try {
            fileNetService.updateDocumentContent(guid, file.getInputStream(), file.getContentType());
            return ResponseEntity.ok("Document content updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating content: " + e.getMessage());
        }
    }
}
