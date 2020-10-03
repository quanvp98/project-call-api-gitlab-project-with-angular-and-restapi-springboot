package com.example.demo.constant;

public class ErrorMessage {
	
	private String message;
	private String treatment;
	
	
	public ErrorMessage(String message, String treatment) {
		super();
		this.message = message;
		this.treatment = treatment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	

}
