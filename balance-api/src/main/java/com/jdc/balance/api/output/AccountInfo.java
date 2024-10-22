package com.jdc.balance.api.output;

import java.time.LocalDateTime;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.AccountActivity_;
import com.jdc.balance.model.entity.Account_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record AccountInfo(
		String name,
		String email,
		LocalDateTime entryAt,
		LocalDateTime lastAccessAt,
		long ledgers,
		long lastMonthEntry,
		long totalEntry) {

	public static void select(CriteriaQuery<AccountInfo> cq, Root<Account> root) {
		
		var activity = root.join(Account_.activity, JoinType.LEFT);
		
		cq.multiselect(
			root.get(Account_.name),
			root.get(Account_.email),
			root.get(Account_.entryAt),
			activity.get(AccountActivity_.lastAccess),
			activity.get(AccountActivity_.totalLedgers),
			activity.get(AccountActivity_.lastMonthEntries),
			activity.get(AccountActivity_.totalEntries)
		);
	}
}
