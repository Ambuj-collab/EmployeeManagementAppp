package com.spring.boot.application.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
	
	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
