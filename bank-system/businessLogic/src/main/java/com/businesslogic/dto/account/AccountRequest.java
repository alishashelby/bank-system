package com.businesslogic.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object for account management")
public class AccountRequest {
    @Schema(description = "Account ID", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Account id field must be filled")
    private Integer accountId;

    @Schema(description = "User login", example = "alishashelby", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "User login field must be filled")
    private String userLogin;

    public int getAccountId() { return this.accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public String getUserLogin() { return this.userLogin; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }
}
