package com.businesslogic.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String id) {
        super("User " + id + " not found");
    }
}
