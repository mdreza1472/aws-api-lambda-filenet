package com.rezatechie.filenet;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.rezatechie.context.StageContextHolder;

@Component
public class FNConnection {

    @Autowired
    private StageContextHolder stageContextHolder;

    public ObjectStore getObjectStore() {
        String uri = stageContextHolder.get("filenetUri");
        String username = stageContextHolder.get("filenetUsername");
        String password = stageContextHolder.get("filenetPassword");
        String objectStoreName = stageContextHolder.get("filenetObjectStore");

        if (uri == null || username == null || password == null || objectStoreName == null) {
            throw new IllegalStateException("Missing required FileNet configuration from stage variables.");
        }

        Connection connection = Factory.Connection.getConnection(uri);
        Subject subject = UserContext.createSubject(connection, username, password, null);
        UserContext.get().pushSubject(subject);

        Domain domain = Factory.Domain.fetchInstance(connection, null, null);
        
		return Factory.ObjectStore.fetchInstance(domain, objectStoreName, null);		
    }
}
