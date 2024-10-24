package com.jdc.balance.exceptions;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jdc.balance.security.JwtTokenExpirationException;
import com.jdc.balance.security.JwtTokenInvalidateException;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	List<String> handle(ApiBaseException e) {
		return e.getErrors();
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	List<String> handle(JwtTokenInvalidateException e) {
		return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
	List<String> handle(JwtTokenExpirationException e) {
		return List.of(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	List<String> handle(AccessDeniedException e) {
		return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	List<String> handle(Throwable e) {
		return List.of(e.getMessage());
	}
	
	
}
