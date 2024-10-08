package com.jdc.balance.api.output;

import java.time.LocalDateTime;

public record AccountInfo(
		long id,
		String name,
		String email,
		LocalDateTime entryAt,
		LocalDateTime lastAccessAt,
		long ledgers,
		long lastMonthEntry,
		long totalEntry) {

}
