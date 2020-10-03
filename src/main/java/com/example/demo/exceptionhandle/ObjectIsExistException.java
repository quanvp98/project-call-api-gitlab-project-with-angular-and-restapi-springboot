package com.example.demo.exceptionhandle;

public class ObjectIsExistException extends RuntimeException {
	private String messageError;
	private String treatmentError;
	
	public ObjectIsExistException(String messageError, String treatmentError) {
		super();
		this.messageError = messageError;
		this.treatmentError = treatmentError;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public String getTreatmentError() {
		return treatmentError;
	}

	public void setTreatmentError(String treatmentError) {
		this.treatmentError = treatmentError;
	}
	
}
