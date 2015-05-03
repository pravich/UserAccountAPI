package com.yggdrasil.account.directory;

public class InvalidCredentialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2971216399726315975L;
	
	InvalidCredentialException(String message) {
		super(message);
	}
}
