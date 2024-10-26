package com.jdc.balance.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private AccountBalance balance;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private AccountActivity activity;
	
	public void setBalance(AccountBalance balance) {
		this.balance = balance;
		balance.setAccount(this);
		balance.setAccountId(email);
	}
	
	public void setActivity(AccountActivity activity) {
		this.activity = activity;
		activity.setAccount(this);
		activity.setAccountId(email);
	}
	
	public enum Role {
		Admin,
		Member
	}

}