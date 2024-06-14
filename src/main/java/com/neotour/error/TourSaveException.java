package com.neotour.error;

public class TourSaveException extends RuntimeException {
    public TourSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
