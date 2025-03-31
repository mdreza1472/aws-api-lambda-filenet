package com.rezatechie.dto;

import java.util.Map;

public class DocumentTO {

    private String guid;
    private Map<String, Object> properties;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
