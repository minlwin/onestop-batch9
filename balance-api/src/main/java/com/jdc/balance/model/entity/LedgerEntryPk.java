package com.jdc.balance.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntryPk {

	@Column(name = "account_id")
	private String accountId;
	@Column(name = "entry_date")
	private LocalDate entryDate;
	@Column(name = "seq_number")
	private int seqNumber;
	
	public static LedgerEntryPk from(LedgerEntrySeq seq) {
		return new LedgerEntryPk(seq.getId().getAccountId(), seq.getId().getEntryDate(), seq.getSeqNumber());
	}

}