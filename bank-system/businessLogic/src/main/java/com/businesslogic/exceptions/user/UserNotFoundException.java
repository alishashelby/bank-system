package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class AlreadyFriendsException is a subclass of BLException.
 * Is thrown when user is not found.
 */
public class UserNotFoundException extends BLException {
    public UserNotFoundException(String id) {
        // Call the parent constructor with super
        super("User " + id + " not found");
    }
}
