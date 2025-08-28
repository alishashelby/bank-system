package com.dataaccess.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class AccountEvent {
    @Id
    private String id;
    private int accountId;
    private String eventType;
    private Object eventData;

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Object getEventData() { return eventData; }
    public void setEventData(Object eventData) { this.eventData = eventData; }
}
