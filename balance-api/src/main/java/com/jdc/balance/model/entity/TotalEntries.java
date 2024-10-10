package com.jdc.balance.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class TotalEntries {

	private String accountId;

	private Account account;

	private int entries;

}