package com.spring.boot.application.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
