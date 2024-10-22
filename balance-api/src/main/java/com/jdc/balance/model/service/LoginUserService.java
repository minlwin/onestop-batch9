package com.jdc.balance.model.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.exceptions.ApiBusinessException;
import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserService {
	
	private final AccountRepo repo;

	@Transactional(readOnly = true)
	public Account getLoginUser() {
		
		return Optional.ofNullable(SecurityContextHolder.getContext())
			.map(a -> a.getAuthentication())
			.map(a -> a.getName())
			.flatMap(username -> repo.findById(username))
			.orElseThrow(() -> new ApiBusinessException("Invalid login user"));
	}

}
