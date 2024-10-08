package com.jdc.balance.api.input;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.PathVariable;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

public record LedgerEntrySearch(
		@PathVariable LedgerType type,
		LocalDate from,
		LocalDate to,
		String ledger) {

}
