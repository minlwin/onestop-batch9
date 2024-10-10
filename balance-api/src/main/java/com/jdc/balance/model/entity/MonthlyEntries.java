package com.jdc.balance.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MonthlyEntries {

	@Embedded
	private MonthlyEntiriesPk id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", referencedColumnName = "email", insertable = false, updatable = false)
	private Account account;

	private int entries;

}