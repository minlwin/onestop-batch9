package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

public record LedgerInfo(
		LedgerType type,
		String code,
		String accountName) {

	public static LedgerInfo from(LedgerAccount ledger) {
		return new LedgerInfo(ledger.getType(), ledger.getCode(), ledger.getLedger());
	}
}
