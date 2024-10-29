package com.jdc.balance.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SummaryChartData(
	LocalDate date,
	BigDecimal debit,
	BigDecimal credit
) {

}
