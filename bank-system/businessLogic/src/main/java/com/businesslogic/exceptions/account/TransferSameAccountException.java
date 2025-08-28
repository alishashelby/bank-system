package com.businesslogic.exceptions.account;

import com.businesslogic.exceptions.BLException;

/**
 * Public class TransferSameAccountException is a subclass of BLException.
 * Is thrown when there is an attempt to transfer money to ourselves.
 */
public class TransferSameAccountException extends BLException {
    public TransferSameAccountException(int id) {
        // Call the parent constructor with super
        super("Cannot transfer same account with id: " + id);
    }
}
