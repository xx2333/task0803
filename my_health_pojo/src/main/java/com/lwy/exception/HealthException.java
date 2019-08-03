package com.lwy.exception;

public class HealthException extends RuntimeException {
    public HealthException() {
    }

    public HealthException(String message) {
        super(message);
    }
}
