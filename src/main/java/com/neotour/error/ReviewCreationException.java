package com.neotour.error;

public class ReviewCreationException extends RuntimeException {
    public ReviewCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
