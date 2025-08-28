package com.dataaccess.entities;

import com.dataaccess.enums.TransactionType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Public class Account represents the entity account of user.
 */
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    private int accountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Transaction> transactionHistory;

    protected Account() {
        transactionHistory = new ArrayList<>();
    }

    /**
     * Initializes a new instance of the Account class.
     * @param user the user who decided to create the account - entity User.
     */
    public Account(int accountId, User user) {
        this.accountId = accountId;
        this.user = user;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Gets id of account.
     * @return int - id.
     */
    public int getId() { return this.accountId; }

    /**
     * Gets the user's login who account belongs to.
     * @return String - the user's login.
     */
    public User getUser() { return this.user; }

    /**
     * Sets the user who account belongs to.
     * @param user - entity User.
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Gets the balance of account.
     * @return double - amount of money in this account.
     */
    public double getBalance() { return this.balance; }

    /**
     * Void method to deposit money on account.
     * @param amount the amount of money which is wanted to deposit.
     */
    public void deposit(double amount) {
        deposit(amount, null);
    }

    /**
     * Overloaded method Deposit to set the different transaction type.
     * @param amount the amount of money which is wanted to deposit.
     * @param transactionType the type of transaction which is DEPOSIT or TRANSFER_FROM.
     */
    public void deposit(double amount, TransactionType transactionType) {
        if (transactionType == null) {
            transactionType = TransactionType.DEPOSIT;
        }

        Transaction transaction = new Transaction(amount, Calendar.getInstance().getTime(), transactionType, this);
        transactionHistory.add(transaction);
        balance += amount;
    }

    /**
     * Void method to withdraw money from account.
     * @param amount the amount of money which is wanted to withdraw.
     */
    public void withdraw(double amount) {
        withdraw(amount, null);
    }

    /**
     * Overloaded method Withdraw to set the different transaction type.
     * @param amount the amount of money which is wanted to withdraw.
     * @param transactionType the type of transaction which is WITHDRAW or TRANSFER_TO.
     */
    public void withdraw(double amount, TransactionType transactionType) {
        if (transactionType == null) {
            transactionType = TransactionType.WITHDRAW;
        }

        Transaction transaction = new Transaction(amount, Calendar.getInstance().getTime(), transactionType, this);
        transactionHistory.add(transaction);
        balance -= amount;
    }
}
