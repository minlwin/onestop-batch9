package com.jdc.balance.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LedgerEntryInfo(
		String trxId,
		LocalDate issueAt,
		String ledger,
		String particular,
		int items,
		BigDecimal amount
) {

}
