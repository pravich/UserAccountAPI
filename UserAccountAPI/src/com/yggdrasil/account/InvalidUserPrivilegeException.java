package com.yggdrasil.account;

public class InvalidUserPrivilegeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597788677477367500L;
	
	static String defaultMessage = "Not enought privilege for this operation.";
	
	InvalidUserPrivilegeException() {
		super(defaultMessage);
	}
	InvalidUserPrivilegeException(String message) {
		super(message);
	}
}
