package com.jdc.balance.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
	
	public enum Type {
		Access, Refresh
	}

	public Authentication parse(String token) {
		return null;
	}

	public String generate(Authentication authentication, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

}
