package com.jdc.balance.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerEntry extends AbstractEntity {

	private LedgerEntryPK id;

	private LedgerAccount ledger;

	private LocalDate issueAt;

	private String particular;

	private BigDecimal lastBalance;

	private BigDecimal totalAmount;

}