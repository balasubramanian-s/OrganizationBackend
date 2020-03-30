package com.revature.organization.exception;

public class BadResponse extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int statusCode;
	private String message;

	public BadResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
}
