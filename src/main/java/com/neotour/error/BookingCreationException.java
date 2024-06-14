package com.neotour.error;

public class BookingCreationException extends RuntimeException {
    public BookingCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
