package com.jdc.balance.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class AccountAccess {

	private String accountId;

	private Account account;

	private LocalDateTime lastAccess;

}