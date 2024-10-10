package com.jdc.balance.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerAccount extends AbstractEntity {

	private String code;

	private Account account;

	private String ladgerName;

	private LedgerType type;

	private List<LedgerEntry> entry;

	public enum LedgerType {
		Debit,
		Credit
	}

}