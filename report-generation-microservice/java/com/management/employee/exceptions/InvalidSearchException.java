package com.management.employee.exceptions;

public class InvalidSearchException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidSearchException(String msg) {
		super(msg);
	}
}
