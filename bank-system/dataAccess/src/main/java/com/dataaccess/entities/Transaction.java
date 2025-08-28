package com.dataaccess.entities;

import com.dataaccess.enums.TransactionType;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

/**
 * Public class Transaction represents the entity transaction.
 */
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    protected Transaction() {}

    /**
     * Initializes a new instance of the Transaction class.
     * @param amount the amount of money which is transferred - double.
     * @param date the date of transfer - Date.
     * @param type the type of transaction - enum.
     */
    public Transaction(double amount, Date date, TransactionType type, Account account) {
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.account = account;
    }

    /**
     * Gets the amount of transaction.
     * @return double - amount of money.
     */
    public double getAmount() { return this.amount; }

    /**
     * Gets date of transaction.
     * @return Date - time of transaction.
     */
    public Date getDate() { return this.date; }

    /**
     * Gets the type of transaction.
     * @return TransactionType - enum.
     */
    public TransactionType getType() { return this.type; }

    /**
     * Gets the account.
     * @return Account - entity.
     */
    public Account getAccount() { return this.account; }
}
