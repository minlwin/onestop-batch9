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
import com.jdc.balance.api.output.SecurityInfo;

@RestController
@RequestMapping("security")
public class SecurityApi {

	@PostMapping("signin")
	SecurityInfo signIn(
			@Validated @RequestBody SignInForm form, BindingResult result) {
		
		return null;
	}
	
	@PostMapping("signup")
	SecurityInfo signUp(
			@Validated @RequestBody SignUpForm form, BindingResult result) {

		return null;
	}
	
	@PostMapping("refresh")
	SecurityInfo refresh(
			@Validated @RequestBody RefreshForm form, BindingResult result) {

		return null;
	}
	
}
