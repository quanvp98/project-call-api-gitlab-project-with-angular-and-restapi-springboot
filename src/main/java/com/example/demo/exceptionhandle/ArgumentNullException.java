package com.example.demo.exceptionhandle;

public class ArgumentNullException extends RuntimeException {
	private String messageError;
	
	public ArgumentNullException(String messageError) {
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
