package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class MyselfFriendException is a subclass of BLException.
 * Is thrown when there is an attempt to friend themselves.
 */
public class MyselfFriendException extends BLException {
    public MyselfFriendException(String login) {
        // Call the parent constructor with super
        super("Cannot make (un)friend with the same login: " + login);
    }
}
