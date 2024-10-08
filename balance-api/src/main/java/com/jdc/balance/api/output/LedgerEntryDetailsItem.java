package com.jdc.balance.api.output;

public record LedgerEntryDetailsItem(
		String entryInfo,
		int unitPrice,
		int quantity) {

}
