package com.rastatech.secretrasta.exceptions;

public class PhoneNumberNotAvailableException extends RuntimeException {
    public PhoneNumberNotAvailableException(String phoneNumber) {
        super(String.format("Phone number %s is not available", phoneNumber));
    }
}
