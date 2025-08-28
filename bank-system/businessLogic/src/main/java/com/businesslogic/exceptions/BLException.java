package com.businesslogic.exceptions;

/**
 * Public class BLException is a subclass of Exception.
 */
public class BLException extends Exception{
    /**
     * Initializes a new instance of the BLException class.
     * @param message a message of exception - String
     */
    public BLException(String message) {
        // Call the parent constructor with super
        super(message);
    }
}
