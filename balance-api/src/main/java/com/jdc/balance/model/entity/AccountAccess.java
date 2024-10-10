package com.jdc.balance.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class AccountAccess {

	@Id
	private String accountId;

	@OneToOne(optional = false)
	@MapsId("accountId")
	private Account account;

	private LocalDateTime lastAccess;

}