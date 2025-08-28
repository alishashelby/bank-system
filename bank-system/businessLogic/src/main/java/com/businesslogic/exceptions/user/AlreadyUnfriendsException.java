package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class AlreadyUnfriendsException is a subclass of BLException.
 * Is thrown when users are already not friends.
 */
public class AlreadyUnfriendsException extends BLException {
    public AlreadyUnfriendsException(String login) {
        // Call the parent constructor with super
        super("Already unfriends with login: " + login);
    }
}
