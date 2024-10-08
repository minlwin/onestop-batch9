package com.jdc.balance.api.input;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordForm(
		@NotBlank(message = "Please enter login id.") String username,
		@NotBlank(message = "Please enter old password.") String oldPassword,
		@NotBlank(message = "Please enter new password.") String newPassword
		){

}
