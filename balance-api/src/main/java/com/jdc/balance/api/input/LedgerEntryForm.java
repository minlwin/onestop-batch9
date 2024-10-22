package com.jdc.balance.api.input;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LedgerEntryForm(
		@NotEmpty(message = "Please enter ledger code") String ledgerCode,
		@NotNull(message = "Please enter issue date.") LocalDate issueAt,
		String particular,
		List<@Valid LedgerEntryFormItem> items
		) {

}
