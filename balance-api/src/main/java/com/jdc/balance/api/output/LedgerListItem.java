package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

public record LedgerListItem(
		String code,
		String accountName,
		LedgerType type,
		long total) {

	public Long getCreditTotal() {
		return type == LedgerType.Credit ? total : null;
	}
	
	public Long getDebitTotal() {
		return type == LedgerType.Debit ? total : null;
	}
}
