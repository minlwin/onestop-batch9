package com.jdc.balance.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntrySeqPk {

	private String accountId;
	private LocalDate entryDate;

}