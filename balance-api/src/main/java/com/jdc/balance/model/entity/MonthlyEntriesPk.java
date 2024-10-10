package com.jdc.balance.model.entity;

import java.time.Month;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MonthlyEntriesPk {

	private int year;

	private Month month;

	@Column(name = "account_id")
	private String accountId;

}