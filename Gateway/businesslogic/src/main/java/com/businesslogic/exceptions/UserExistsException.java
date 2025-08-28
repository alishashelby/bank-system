package com.businesslogic.exceptions;

public class UserExistsException extends Exception {
    public UserExistsException(String login) {
        super("User with login '" + login + "' already exists");
    }
}
