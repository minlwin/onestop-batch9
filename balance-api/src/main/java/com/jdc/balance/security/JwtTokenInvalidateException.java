package com.jdc.balance.security;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenInvalidateException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public JwtTokenInvalidateException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
