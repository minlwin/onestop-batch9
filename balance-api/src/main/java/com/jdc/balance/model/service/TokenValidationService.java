package com.jdc.balance.model.service;

import org.springframework.stereotype.Service;

import com.jdc.balance.api.input.TokenValidationForm;
import com.jdc.balance.api.output.TokenValidationResult;
import com.jdc.balance.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenValidationService {
	
	private final JwtTokenProvider provider;

	public TokenValidationResult validate(TokenValidationForm form) {
		
		try {
			provider.parse(form.token(), form.type());
			return new TokenValidationResult(true);
		} catch (Exception e) {
			return new TokenValidationResult(false);
		}
	}

}
