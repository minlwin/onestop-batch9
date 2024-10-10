package com.jdc.balance.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class TotalEntries {

	@Id
	private String accountId;

	@OneToOne(optional = false)
	@MapsId(value = "accountId")
	private Account account;

	private int entries;

}