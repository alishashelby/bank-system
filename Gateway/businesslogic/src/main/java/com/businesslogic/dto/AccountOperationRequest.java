package com.businesslogic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AccountOperationRequest {
    @NotNull(message = "Account id field must be filled")
    private Integer accountId;

    private String userLogin;

    @Positive(message = "Value of amount must be positive")
    private double amount;

    public int getAccountId() { return this.accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public String getUserLogin() { return this.userLogin; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }
    public double getAmount() { return this.amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
