package com.businesslogic.exceptions.account;

import com.businesslogic.exceptions.BLException;

/**
 * Public class NonPositiveAmountException is a subclass of BLException.
 * Is thrown when there is an attempt to transfer non-positive amount.
 */
public class NonPositiveAmountException extends BLException {
    public NonPositiveAmountException(double amount) {
        // Call the parent constructor with super
        super(amount + " should be positive");
    }
}
