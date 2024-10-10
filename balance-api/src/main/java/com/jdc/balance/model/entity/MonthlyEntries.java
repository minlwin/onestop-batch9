package com.jdc.balance.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MonthlyEntries {

	@EmbeddedId
	private MonthlyEntriesPk id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", referencedColumnName = "email", insertable = false, updatable = false)
	private Account account;

	private int entries;

}