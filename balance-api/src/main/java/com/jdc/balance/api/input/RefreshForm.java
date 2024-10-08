package com.jdc.balance.api.input;

import jakarta.validation.constraints.NotBlank;

public record RefreshForm(
		@NotBlank(message = "Plase enter refresh token.") String token) {

}
