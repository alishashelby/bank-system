package com.businesslogic.exceptions.account;

import com.businesslogic.exceptions.BLException;

/**
 * Public class AccountExistsException is a subclass of BLException.
 * Is thrown when account with given id is already exists.
 */
public class AccountExistsException extends BLException {
    public AccountExistsException(int id) {
        // Call the parent constructor with super
        super("account with id " + id + " already exists");
    }
}
