package com.jdc.balance.api;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.input.RefreshForm;
import com.jdc.balance.api.input.SignInForm;
import com.jdc.balance.api.input.SignUpForm;
import com.jdc.balance.api.input.TokenValidationForm;
import com.jdc.balance.api.output.SecurityInfo;
import com.jdc.balance.api.output.TokenValidationResult;
import com.jdc.balance.model.service.AccountSecurityService;
import com.jdc.balance.model.service.TokenValidationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("security")
public class SecurityApi {
	
	private final AccountSecurityService securityService;
	private final TokenValidationService validationService;

	@PostMapping("signin")
	SecurityInfo signIn(
			@Validated @RequestBody SignInForm form, BindingResult result) {
		return securityService.signIn(form);
	}
	
	@PostMapping("signup")
	SecurityInfo signUp(
			@Validated @RequestBody SignUpForm form, BindingResult result) {
		return securityService.signUp(form);
	}
	
	@PostMapping("refresh")
	SecurityInfo refresh(
			@Validated @RequestBody RefreshForm form, BindingResult result) {
		return securityService.refresh(form);
	}
	
	@PostMapping("validate")
	TokenValidationResult validate(TokenValidationForm form, BindingResult result) {
		return validationService.validate(form);
	}
	
}
