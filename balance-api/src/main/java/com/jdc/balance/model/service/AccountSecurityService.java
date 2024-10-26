package com.jdc.balance.model.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.input.ChangePasswordForm;
import com.jdc.balance.api.input.RefreshForm;
import com.jdc.balance.api.input.SignInForm;
import com.jdc.balance.api.input.SignUpForm;
import com.jdc.balance.api.output.SecurityInfo;
import com.jdc.balance.exceptions.ApiBusinessException;
import com.jdc.balance.model.entity.Account;
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
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public SecurityInfo chagePassword(ChangePasswordForm form) {
		
		var account = accountRepo.findById(form.username())
				.orElseThrow(() -> new ApiBusinessException("Invalid username."));
		
		if(!passwordEncoder.matches(form.oldPassword(), account.getPassword())) {
			throw new ApiBusinessException("Please check your old password.");
		}
		
		account.setPassword(passwordEncoder.encode(form.newPassword()));
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return getResult(account, authentication);
	}

	@Transactional
	public SecurityInfo signUp(SignUpForm form) {
		
		if(accountRepo.findById(form.username()).isPresent()) {
			throw new ApiBusinessException("Your account is already registered. Please check your email.");
		}
		
		var account = accountRepo.save(form.entity(passwordEncoder::encode));
		var authentication = authenticationManager.authenticate(form.authentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return getResult(account, authentication);
	}

	public SecurityInfo signIn(SignInForm form) {
		
		var authentication = authenticationManager.authenticate(form.authentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var account = accountRepo.findById(form.username())
				.orElseThrow(() -> new ApiBusinessException("Invalid username."));
		
		return getResult(account, authentication);
	}

	public SecurityInfo refresh(RefreshForm form) {
		var authentication = tokenProvider.parse(form.token(), Type.Refresh);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var account = accountRepo.findById(authentication.getName())
				.orElseThrow(() -> new ApiBusinessException("Invalid username."));
		
		return getResult(account, authentication);
	}
	
	private SecurityInfo getResult(Account account, Authentication authentication) {
		return SecurityInfo.builder()
				.loginId(account.getEmail())
				.name(account.getName())
				.role(account.getRole())
				.accessToken(tokenProvider.generate(authentication, Type.Access))
				.refreshToken(tokenProvider.generate(authentication, Type.Refresh))
				.build();
	}

}
