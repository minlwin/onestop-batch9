package com.jdc.balance.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerEntry extends AbstractEntity {

	@EmbeddedId
	private LedgerEntryPK id;

	@ManyToOne(optional = false)
	private LedgerAccount ledger;

	@Column(nullable = false)
	private LocalDate issueAt;

	private String particular;

	@Column(nullable = false)
	private BigDecimal lastBalance;

	@Column(nullable = false)
	private BigDecimal totalAmount;

}