package com.jdc.balance.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AccountBalance extends AbstractEntity {

	@Id
	private String accountId;
	
	@OneToOne(optional = false)
	@MapsId("accountId")
	private Account account;
	
	private BigDecimal amount;

}