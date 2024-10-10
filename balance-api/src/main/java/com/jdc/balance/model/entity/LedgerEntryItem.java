package com.jdc.balance.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LedgerEntryItem {

	public LedgerEntryItem() {
	}

	private LedgerEntryItemPk id;

	private LedgerEntry entry;

	private String entryInfo;

	private BigDecimal unitPrice;

	private int quantity;

}