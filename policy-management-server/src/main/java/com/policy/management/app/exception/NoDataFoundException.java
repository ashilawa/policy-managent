package com.policy.management.app.exception;

public class NoDataFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4577139870596831334L;

	public NoDataFoundException(String message) {
        super(message);
    }
}
