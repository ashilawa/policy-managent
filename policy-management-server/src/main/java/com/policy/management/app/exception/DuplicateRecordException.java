package com.policy.management.app.exception;

public class DuplicateRecordException extends RuntimeException {

    public DuplicateRecordException(String message) {
        super(message);
    }
}