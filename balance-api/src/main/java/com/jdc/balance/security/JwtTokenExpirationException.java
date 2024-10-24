package com.jdc.balance.security;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpirationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public JwtTokenExpirationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
