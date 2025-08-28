package com.businesslogic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AccountTransferRequest {
    @NotNull(message = "Account id field must be filled")
    private Integer fromAccountId;

    private String userLogin;

    @NotNull(message = "Account id field must be filled")
    private Integer toAccountId;

    @Positive(message = "Value of amount must be positive")
    private double amount;

    public int getFromAccountId() { return this.fromAccountId; }
    public void setFromAccountId(int accountId) { this.fromAccountId = accountId; }

    public int getToAccountId() { return this.toAccountId; }
    public void setToAccountId(int accountId) { this.toAccountId = accountId; }
    public String getUserLogin() { return this.userLogin; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }
    public double getAmount() { return this.amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
