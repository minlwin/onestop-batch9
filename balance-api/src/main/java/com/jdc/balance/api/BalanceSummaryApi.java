package com.jdc.balance.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.output.SummaryData;
import com.jdc.balance.model.service.BalanceSummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("summary")
public class BalanceSummaryApi {
	
	private final BalanceSummaryService service;

	@GetMapping
	SummaryData getData() {
		return service.load();
	}
}
