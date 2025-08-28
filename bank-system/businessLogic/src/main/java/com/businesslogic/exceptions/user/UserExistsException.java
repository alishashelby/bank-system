package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class UserExistsException is a subclass of BLException.
 * Is thrown when user is already exists.
 */
public class UserExistsException extends BLException {
    public UserExistsException(String login) {
        // Call the parent constructor with super
        super("User with login '" + login + "' already exists");
    }
}
