package com.businesslogic.dto;

import java.util.Date;

public class TransactionDTO {
    public int accountId;
    public double amount;
    public Date date;
    public String type;

    public void setAccountId(int accountId) { this.accountId = accountId; }
    public int getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(Date date) { this.date = date; }
    public void setType(String type) { this.type = type; }
    public Date getDate() { return date; }
    public String getType() { return type; }
}
