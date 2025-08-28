package com.businesslogic.exceptions.account;

import com.businesslogic.exceptions.BLException;

/**
 * Public class InsufficientBalanceException is a subclass of BLException.
 * Is thrown when the balance is insufficient to transfer given amount.
 */
public class InsufficientBalanceException extends BLException {
    public InsufficientBalanceException(double amount) {
        // Call the parent constructor with super
        super("Insufficient balance because of " + amount);
    }
}
