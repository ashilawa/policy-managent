package com.policy.management.app.exception;

public class DuplicateRecordException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7287886981674017354L;

	public DuplicateRecordException(String message) {
        super(message);
    }
}