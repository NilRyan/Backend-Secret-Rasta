package com.rastatech.secretrasta.exceptions;

public class UserNameNotAvailableException extends RuntimeException {
    public UserNameNotAvailableException(String username) {
        super(String.format("Username %s is not available", username));
    }
}
