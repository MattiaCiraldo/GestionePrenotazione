package it.mattiaciraldo.spring.controller;

public class SignupResponse {
	private String message;

	public SignupResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
