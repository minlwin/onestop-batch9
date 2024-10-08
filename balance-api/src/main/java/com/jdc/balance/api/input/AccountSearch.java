package com.jdc.balance.api.input;

import java.time.LocalDate;

public record AccountSearch(
		LocalDate entryFrom,
		LocalDate entryTo,
		LocalDate lastAccessFrom,
		LocalDate lastAccessTo,
		String keyword) {

}
