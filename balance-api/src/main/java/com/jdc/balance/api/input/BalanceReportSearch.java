package com.jdc.balance.api.input;

import java.time.LocalDate;

public record BalanceReportSearch(
		LocalDate from,
		LocalDate to,
		String ledger) {

}
