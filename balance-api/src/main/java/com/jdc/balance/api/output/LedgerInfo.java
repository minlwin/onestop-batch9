package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerAccount;

public record LedgerInfo(
		String code,
		String accountName) {

	public static LedgerInfo from(LedgerAccount ledger) {
		return new LedgerInfo(ledger.getCode(), ledger.getLedger());
	}
}
