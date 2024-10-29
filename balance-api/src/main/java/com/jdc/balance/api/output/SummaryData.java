package com.jdc.balance.api.output;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SummaryData {

	private BigDecimal lastCredit;
	private BigDecimal lastDebit;
	private BigDecimal totalCredit;
	private BigDecimal totalDebit;
	
	private List<SummaryChartData> chartData;
	
	public BigDecimal getTotalBalance() {
		return totalCredit.subtract(totalDebit);
	}
}
