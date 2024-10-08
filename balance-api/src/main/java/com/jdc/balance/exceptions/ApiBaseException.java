package com.jdc.balance.exceptions;

import java.util.ArrayList;
import java.util.List;

public abstract class ApiBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private List<String> errors = new ArrayList<>();
	
	public ApiBaseException() {
	}

	public ApiBaseException(String message, Throwable cause) {
		super(message, cause);
		errors.add(message);
	}

	public ApiBaseException(String message) {
		super(message);
		errors.add(message);
	}
	
	public List<String> getErrors() {
		return errors;
	}

}
