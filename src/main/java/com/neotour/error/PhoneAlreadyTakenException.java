package com.neotour.error;

public class PhoneAlreadyTakenException extends RuntimeException {
    public PhoneAlreadyTakenException(String message) {
        super(message);
    }
}
