package com.rastatech.secretrasta.exceptions;

public class EmailNotAvailableException extends RuntimeException {
    public EmailNotAvailableException(String email) {
        super(String.format("Email %s is not available", email));
    }
}
