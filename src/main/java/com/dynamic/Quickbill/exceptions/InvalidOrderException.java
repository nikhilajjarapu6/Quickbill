package com.dynamic.Quickbill.exceptions;

public class InvalidOrderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOrderException(String message) {
		super(message);
	}
	

}
