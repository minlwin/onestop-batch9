package com.jdc.balance.model.entity;

import java.time.Month;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MonthlyEntiriesPk {

	private int year;

	private Month month;

	private String accountId;

}