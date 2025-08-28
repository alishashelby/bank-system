package com.businesslogic.dto;

import jakarta.validation.constraints.NotNull;

public class AccountRequest {
    @NotNull(message = "Account id field must be filled")
    private Integer accountId;

    private String userLogin;

    public int getAccountId() { return this.accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public String getUserLogin() { return this.userLogin; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }
}
