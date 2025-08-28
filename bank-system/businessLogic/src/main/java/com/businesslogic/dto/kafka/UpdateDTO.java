package com.businesslogic.dto.kafka;

public class UpdateDTO {
    private String operation;
    private String value;

    public UpdateDTO() {}

    public UpdateDTO(String operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    public String getOperation() { return this.operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getValue() { return this.value; }
    public void setValue(String value) { this.value = value; }
}
