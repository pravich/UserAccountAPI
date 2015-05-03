package com.yggdrasil.account.directory;

public class ConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 721998568178711984L;

	ConnectionException() {
		super();
	}
	
	ConnectionException(String meassage, Throwable cause) {
		super(meassage, cause);
	}
}
