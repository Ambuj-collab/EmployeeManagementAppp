package com.spring.boot.application.exceptions;

public class ErrorObject {

	private int status;
	private String message;
	private String timestamp;

	public ErrorObject(int status, String message, String timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public ErrorObject() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorObject [status=" + status + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
}
