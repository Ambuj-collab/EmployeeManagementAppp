package com.spring.boot.application.exceptions;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleStringToJsonParsingException(StringToJsonParsingException ex){
		ErrorObject errorObject = getErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());  
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex){
		ErrorObject errorObject = getErrorObject(HttpStatus.CONFLICT.value(), ex.getMessage());  
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException ex){
		ErrorObject errorObject = getErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());  
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
	}
	
	private ErrorObject getErrorObject(int status, String message) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ErrorObject errorObject = new ErrorObject(status, message, timestamp.toString());
		return errorObject;
	}

}
