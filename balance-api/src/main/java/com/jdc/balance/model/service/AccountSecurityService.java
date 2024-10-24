package com.jdc.balance.model.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.input.ChangePasswordForm;
import com.jdc.balance.api.input.RefreshForm;
import com.jdc.balance.api.input.SignInForm;
import com.jdc.balance.api.input.SignUpForm;
import com.jdc.balance.api.output.SecurityInfo;
import com.jdc.balance.exceptions.ApiBusinessException;
import com.jdc.balance.model.repo.AccountRepo;
import com.jdc.balance.security.JwtTokenProvider;
import com.jdc.balance.security.JwtTokenProvider.Type;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountSecurityService {
	
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;
	private final AccountRepo accountRepo;

	@Transactional
	public SecurityInfo chagePassword(ChangePasswordForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public SecurityInfo signUp(SignUpForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public SecurityInfo signIn(SignInForm form) {
		
		var authentication = authenticationManager.authenticate(form.authentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var account = accountRepo.findById(form.username())
				.orElseThrow(() -> new ApiBusinessException("Invalid username."));
		
		return SecurityInfo.builder()
				.loginId(account.getEmail())
				.name(account.getName())
				.role(account.getRole())
				.accessToken(tokenProvider.generate(authentication, Type.Access))
				.refreshToken(tokenProvider.generate(authentication, Type.Refresh))
				.build();
	}

	public SecurityInfo refresh(RefreshForm form) {
		var authentication = tokenProvider.parse(form.token());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var account = accountRepo.findById(authentication.getName())
				.orElseThrow(() -> new ApiBusinessException("Invalid username."));
		
		return SecurityInfo.builder()
				.loginId(account.getEmail())
				.name(account.getName())
				.role(account.getRole())
				.accessToken(tokenProvider.generate(authentication, Type.Access))
				.refreshToken(tokenProvider.generate(authentication, Type.Refresh))
				.build();
	}

}
