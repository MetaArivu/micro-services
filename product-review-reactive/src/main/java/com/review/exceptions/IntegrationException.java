package com.review.exceptions;

public class IntegrationException extends RuntimeException {
    
	public IntegrationException(String message) {
        super(message);
    }
	
    public IntegrationException(String message, int statusCode) {
        super(message +"|"+statusCode);
    }
}
