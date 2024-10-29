package com.jdc.balance.model.entity;

import java.util.List;

import com.jdc.balance.exceptions.ApiBusinessException;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerAccount extends AbstractEntity {

	@EmbeddedId
	private LedgerAccountPk id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", insertable = false, updatable = false)
	private Account account;

	@Column(nullable = false)
	private String ledger;

	@OneToMany(mappedBy = "ledger")
	private List<LedgerEntry> entry;

	public enum LedgerType {
		Debit,
		Credit;

		public static LedgerType from(char c) {
			return switch(c) {
			case 'C' -> Credit;
			case 'D' -> Debit;
			default -> throw new ApiBusinessException("Invalid ledger code.");
			};
		}
	}

}