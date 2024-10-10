package com.jdc.balance.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class MonthlyEntries {

	private MonthlyEntiriesPk id;

	private Account account;

	private int entries;

}