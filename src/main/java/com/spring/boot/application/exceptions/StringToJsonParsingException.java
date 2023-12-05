package com.spring.boot.application.exceptions;

public class StringToJsonParsingException extends RuntimeException {
	
	public StringToJsonParsingException(String message) {
		super(message);
	}

}
