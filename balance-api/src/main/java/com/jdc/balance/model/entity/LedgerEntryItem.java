package com.jdc.balance.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LedgerEntryItem {
	
	@EmbeddedId
	private LedgerEntryItemPk id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
	@JoinColumn(name = "entry_date", referencedColumnName = "entry_date", insertable = false, updatable = false)
	@JoinColumn(name = "seq_number", referencedColumnName = "seq_number", insertable = false, updatable = false)
	private LedgerEntry entry;

	@Column(nullable = false)
	private String entryInfo;

	@Column(nullable = false)
	private BigDecimal unitPrice;

	private int quantity;

}