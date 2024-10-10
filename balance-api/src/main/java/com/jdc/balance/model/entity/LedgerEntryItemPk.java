package com.jdc.balance.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerEntryItemPk {

	private String accountId;

	private LocalDate entryDate;

	private int seqNumber;

	private int itemSeq;

}