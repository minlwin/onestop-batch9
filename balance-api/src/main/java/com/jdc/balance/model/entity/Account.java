package com.jdc.balance.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Account extends AbstractEntity {

	private String name;

	private String email;

	private String password;

	private String role;

	private LocalDateTime entryAt;

	private AccountBalance balance;

	private AccountAccess access;

	private List<MonthlyEntries> monthlyEntries;

	private TotalEntries totalEntries;

	public enum Role {
		Admin,
		Member
	}

}