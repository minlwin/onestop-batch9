package com.jdc.balance.api.input;

import com.jdc.balance.security.JwtTokenProvider.Type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TokenValidationForm(
		@NotNull(message = "Please select token type.")
		Type type,
		@NotEmpty(message = "Please enter token.")
		String token) {

}
