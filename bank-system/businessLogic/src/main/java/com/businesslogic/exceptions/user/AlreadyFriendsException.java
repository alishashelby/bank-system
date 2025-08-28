package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class AlreadyFriendsException is a subclass of BLException.
 * Is thrown when users are already friends.
 */
public class AlreadyFriendsException extends BLException {
    public AlreadyFriendsException(String login) {
        // Call the parent constructor with super
        super("Already friends with login: " + login);
    }
}
