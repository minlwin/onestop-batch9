package com.jdc.balance.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.input.LedgerEntryForm;
import com.jdc.balance.api.input.LedgerEntrySearch;
import com.jdc.balance.api.output.DataModificationResult;
import com.jdc.balance.api.output.LedgerEntryDetails;
import com.jdc.balance.api.output.LedgerEntryInfo;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.model.service.LedgerEntryService;

@RestController
@RequestMapping("entry/{type}")
public class LedgerEntiryManagementApi {
	
	@Autowired
	private LedgerEntryService service;
	
	@GetMapping
	PageInfo<LedgerEntryInfo> search(LedgerEntrySearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return service.search(search, page, size);
	}
	
	@GetMapping("{trxId}")
	LedgerEntryDetails findById(@PathVariable String trxId) {
		return service.findById(trxId);
	}
	
	@PostMapping
	DataModificationResult<String> create(
			@Validated @RequestBody LedgerEntryForm form, BindingResult result) {
		return service.create(form);
	}
	
}
