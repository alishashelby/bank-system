package com.businesslogic.dto;

import com.dataaccess.entities.Transaction;
import com.dataaccess.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Transaction data transfer object")
public class TransactionDTO {
    @Schema(description = "Account ID", example = "1111")
    public int accountId;

    @Schema(description = "Transaction amount", example = "550.50")
    public double amount;

    @Schema(description = "Transaction date", example = "2025-04-11T12:10:59.923+00:00")
    public Date date;

    @Schema(description = "Transaction type", example = "WITHDRAW")
    public TransactionType type;

    public TransactionDTO(Transaction transaction) {
        this.accountId = transaction.getAccount().getId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.type = transaction.getType();
    }
}
