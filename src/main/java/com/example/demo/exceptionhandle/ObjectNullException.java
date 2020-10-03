package com.example.demo.exceptionhandle;

public class ObjectNullException extends RuntimeException {
	
	private String messageError;

	public ObjectNullException(String messageError) {
		super();
		this.messageError = messageError;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	
}
