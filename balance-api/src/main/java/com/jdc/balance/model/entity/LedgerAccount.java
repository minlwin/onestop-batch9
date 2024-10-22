package com.jdc.balance.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerAccount extends AbstractEntity {

	@Id
	private String code;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(nullable = false)
	private String ledger;

	@Column(nullable = false)
	private LedgerType type;

	@OneToMany(mappedBy = "ledger")
	private List<LedgerEntry> entry;

	public enum LedgerType {
		Debit,
		Credit
	}

}