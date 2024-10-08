package com.jdc.balance.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.input.BalanceReportSearch;
import com.jdc.balance.api.output.BalanceReportInfo;
import com.jdc.balance.api.output.PageInfo;

@RestController
@RequestMapping("report")
public class BalanceReportApi {
	
	@GetMapping
	PageInfo<BalanceReportInfo> search(BalanceReportSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return null;
	}
}
