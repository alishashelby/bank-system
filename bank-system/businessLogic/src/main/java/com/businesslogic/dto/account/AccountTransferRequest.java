package com.businesslogic.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request object for transferring amount between accounts")
public class AccountTransferRequest {
    @Schema(description = "From account ID", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Account id field must be filled")
    private Integer fromAccountId;

    @Schema(description = "From user login", example = "alishashelby", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "User login field must be filled")
    private String userLogin;

    @Schema(description = "To account ID", example = "2222", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Account id field must be filled")
    private Integer toAccountId;

    @Schema(description = "Amount of money to transfer", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
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
