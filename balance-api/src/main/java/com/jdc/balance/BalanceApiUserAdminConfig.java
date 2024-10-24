package com.jdc.balance;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account.Role;
import com.jdc.balance.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BalanceApiUserAdminConfig {
	
	private final AccountRepo repo;
	private final PasswordEncoder encoder;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			if(repo.count() == 0L) {
				var account = new Account();
				account.setName("Admin User");
				account.setEmail("admin@gmail.com");
				account.setPassword(encoder.encode("password"));
				account.setRole(Role.Admin);
				repo.save(account);
			}
		};
	}
}
