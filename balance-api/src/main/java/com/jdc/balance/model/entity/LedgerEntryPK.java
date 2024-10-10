package com.jdc.balance.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntryPK {

	@Column(name = "account_id")
	private String accountId;
	@Column(name = "entry_date")
	private LocalDate entryDate;
	@Column(name = "seq_number")
	private int seqNumber;

}