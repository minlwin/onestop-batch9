package com.jdc.balance.api.output;

import java.time.LocalDate;
import java.util.List;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerEntry;

public record LedgerEntryDetails(
		String trxId,
		LedgerType type,
		String ledgerCode,
		String accountName,
		String particular,
		LocalDate issueAt,
		LocalDate entryAt,
		int total,
		List<LedgerEntryDetailsItem> items) {

	
	public static LedgerEntryDetails from(LedgerEntry entity) {
		return new LedgerEntryDetails(
				entity.getId().getCode(), 
				entity.getLedger().getId().getType(), 
				entity.getLedger().getId().getCode(), 
				entity.getLedger().getLedger(), 
				entity.getParticular(), 
				entity.getIssueAt(), 
				entity.getId().getEntryDate(), 
				entity.getTotalAmount().intValue(), 
				entity.getItems().stream().map(LedgerEntryDetailsItem::from).toList());
	}
}
