package com.jdc.balance.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.input.AccountSearch;
import com.jdc.balance.api.output.AccountInfo;
import com.jdc.balance.api.output.PageInfo;

@RestController
@RequestMapping("account")
public class AccountManagementApi {
	
	@GetMapping
	PageInfo<AccountInfo> search(AccountSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return null;
	}
}
