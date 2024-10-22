package com.jdc.balance.api;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.input.LedgerEditForm;
import com.jdc.balance.api.input.LedgerSearch;
import com.jdc.balance.api.input.LedgerUpdateForm;
import com.jdc.balance.api.output.DataModificationResult;
import com.jdc.balance.api.output.LedgerInfo;
import com.jdc.balance.api.output.LedgerListItem;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.model.service.LedgerService;
import com.jdc.balance.model.service.listener.LedgerChangesEvent;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("ledger")
public class LedgerManagementApi {
	
	private final LedgerService service;
	private final ApplicationEventPublisher publisher;

	@GetMapping
	PageInfo<LedgerListItem> search(LedgerSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return service.search(search, page, size);
	}
	
	@GetMapping("{code}")
	LedgerInfo findById(@PathVariable String code) {
		return service.findById(code);
	}
	
	@PostMapping
	DataModificationResult<String> create(
			@Validated @RequestBody LedgerEditForm form, BindingResult result) {
		var modificationResult = service.create(form);
		publisher.publishEvent(new LedgerChangesEvent(modificationResult.id()));
		return modificationResult;
	}
	
	@PutMapping("{code}")
	DataModificationResult<String> udate(@PathVariable String code,
			@Validated @RequestBody LedgerUpdateForm form, BindingResult result) {
		var modificationResult = service.update(code, form);
		publisher.publishEvent(new LedgerChangesEvent(modificationResult.id()));
		return modificationResult;
	}
}
