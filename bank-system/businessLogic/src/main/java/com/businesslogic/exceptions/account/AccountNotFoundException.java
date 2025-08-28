package com.businesslogic.exceptions.account;

import com.businesslogic.exceptions.BLException;

/**
 * Public class AccountNotFoundException is a subclass of BLException.
 * Is thrown when account with given id does not exist.
 */
public class AccountNotFoundException extends BLException {
    public AccountNotFoundException(int id) {
        // Call the parent constructor with super
        super("Account not found with id: " + id);
    }
}
