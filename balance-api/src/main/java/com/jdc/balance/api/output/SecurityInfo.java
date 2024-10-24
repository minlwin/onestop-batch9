package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.Account.Role;

public record SecurityInfo(
		String loginId,
		String name,
		Role role,
		String accessToken,
		String refreshToken) {
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String loginId;
		private String name;
		private Role role;
		private String accessToken;
		private String refreshToken;
		
		private Builder() {
		}
		
		public SecurityInfo build() {
			return new SecurityInfo(loginId, name, role, accessToken, refreshToken);
		}

		public Builder loginId(String loginId) {
			this.loginId = loginId;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder role(Role role) {
			this.role = role;
			return this;
		}

		public Builder accessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}

		public Builder refreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}
	}
}
