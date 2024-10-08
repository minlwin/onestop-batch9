package com.jdc.balance.api.input;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

public record LedgerSearch(
		LedgerType type,
		String keyword
		) {

}
