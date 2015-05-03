package com.yggdrasil.account;

public class InvalidSessionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5816764979981220663L;
	
	static String defaultMessage = "Session token is invalid.";
	
	InvalidSessionException() {
		super(defaultMessage);
	}
	
	InvalidSessionException(String message) {
		super(message);
	}
}
