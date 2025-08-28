package com.businesslogic.dto.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaEvent {
    private String id;
    private String eventType;
    private Object data;

    public KafkaEvent() {}

    @JsonCreator
    public KafkaEvent(@JsonProperty("id") String id,
                      @JsonProperty("eventType") String eventType,
                      @JsonProperty("data") Object data) {
        this.id = id;
        this.eventType = eventType;
        this.data = data;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
