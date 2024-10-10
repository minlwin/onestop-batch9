package com.jdc.balance.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AccountBalance extends AbstractEntity {

	private String accountId;
	private Account account;
	private BigDecimal amount;

}