package com.jdc.balance.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jdc.balance.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiUserDetailsService implements UserDetailsService{
	
	private final AccountRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findById(username)
				.map(account -> User.builder()
						.username(username)
						.password(account.getPassword())
						.authorities(account.getRole().name())
						.build())
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
