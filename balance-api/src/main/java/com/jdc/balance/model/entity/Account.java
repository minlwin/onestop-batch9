package com.jdc.balance.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Account extends AbstractEntity {

	@Id
	private String email;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Role role;

	private LocalDateTime entryAt;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
	private AccountBalance balance;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
	private AccountAccess access;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
	private TotalEntries totalEntries;

	@OneToMany(mappedBy = "account")
	private List<MonthlyEntries> monthlyEntries;
	
	@OneToMany(mappedBy = "account")
	private List<LedgerAccount> ledgers;

	public enum Role {
		Admin,
		Member
	}

}