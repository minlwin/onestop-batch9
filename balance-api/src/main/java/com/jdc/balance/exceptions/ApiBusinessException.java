package com.jdc.balance.exceptions;

public class ApiBusinessException extends ApiBaseException {

	private static final long serialVersionUID = 1L;

	public ApiBusinessException(String message) {
		super(message);
	}
	
	public ApiBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
