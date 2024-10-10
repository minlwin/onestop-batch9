package com.jdc.balance.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntrySeqPk {

	private String accountId;
	private LocalDate entryDate;
	
	public static LedgerEntrySeqPk now(String accountId) {
		return new LedgerEntrySeqPk(accountId, LocalDate.now());
	}
	
}