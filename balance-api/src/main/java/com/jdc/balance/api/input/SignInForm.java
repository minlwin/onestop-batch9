package com.jdc.balance.api.input;

import jakarta.validation.constraints.NotBlank;

public record SignInForm(
		@NotBlank(message = "Please enter login id.") String username,
		@NotBlank(message = "Please enter password") String password){

}
