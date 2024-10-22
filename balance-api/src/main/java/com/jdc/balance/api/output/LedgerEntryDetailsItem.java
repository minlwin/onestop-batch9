package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerEntryItem;

public record LedgerEntryDetailsItem(
		String entryInfo,
		int unitPrice,
		int quantity) {

	public static LedgerEntryDetailsItem from (LedgerEntryItem entity) {
		return new LedgerEntryDetailsItem(entity.getEntryInfo(), 
				entity.getUnitPrice().intValue(), 
				entity.getQuantity());
	}
}
