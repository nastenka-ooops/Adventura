package com.neotour.error;

public class TourNotFoundException extends RuntimeException {
    public TourNotFoundException(String message) {
        super(message);
    }
}
