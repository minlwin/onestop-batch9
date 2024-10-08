package com.jdc.balance.exceptions;

import org.springframework.validation.BindingResult;

public class ApiValidationException extends ApiBaseException {

	private static final long serialVersionUID = 1L;

	public ApiValidationException(BindingResult result) {
		if(result.hasErrors()) {
			for(var error : result.getFieldErrors()) {
				getErrors().add(error.getDefaultMessage());
			}
		}
	}
}
