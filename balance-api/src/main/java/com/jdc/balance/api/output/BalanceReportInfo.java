package com.jdc.balance.api.output;

import java.time.LocalDate;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

public record BalanceReportInfo(
		LocalDate issueAt,
		String ledgerCode,
		String ledgerAccountName,
		String particular,
		LedgerType type,
		int amount,
		int balance) {

}
