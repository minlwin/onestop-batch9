package com.jdc.balance.api.input;

import jakarta.validation.constraints.NotBlank;

public record SignUpForm(
		@NotBlank(message = "Please enter your name.") String name,
		@NotBlank(message = "Please enter login id.") String username,
		@NotBlank(message = "Please enter password.") String password) {

}
