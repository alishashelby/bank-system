package com.dataaccess.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class ClientEvent {
    @Id
    private String id;
    private String clientLogin;
    private String eventType;
    private Object eventData;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientLogin() { return clientLogin; }
    public void setClientLogin(String clientLogin) { this.clientLogin = clientLogin; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Object getEventData() { return eventData; }
    public void setEventData(Object eventData) { this.eventData = eventData; }
}
