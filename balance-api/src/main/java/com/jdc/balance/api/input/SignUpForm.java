package com.jdc.balance.api.input;

import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account.Role;
import com.jdc.balance.model.entity.AccountActivity;
import com.jdc.balance.model.entity.AccountBalance;

import jakarta.validation.constraints.NotBlank;

public record SignUpForm(
		@NotBlank(message = "Please enter your name.") String name,
		@NotBlank(message = "Please enter login id.") String username,
		@NotBlank(message = "Please enter password.") String password) {

	public Account entity(Function<String, String> encoder) {
		
		var entity = new Account();
		entity.setName(name);
		entity.setEmail(username);
		entity.setPassword(encoder.apply(password));
		
		entity.setActivity(new AccountActivity());
		entity.setBalance(new AccountBalance());
		
		entity.setRole(Role.Member);
		entity.setEntryAt(LocalDateTime.now());
		
		return entity;
	}

	public Authentication authentication() {
		return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
	}

}
