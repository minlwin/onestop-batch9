package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record LedgerListItem(
		String code,
		String accountName,
		LedgerType type,
		long total) {
	
	public static void select(CriteriaBuilder cb, CriteriaQuery<LedgerListItem> cq, Root<LedgerAccount> root) {
		
		var entry = root.join(LedgerAccount_.entry, JoinType.LEFT);
		
		cq.multiselect(
			root.get(LedgerAccount_.code),
			root.get(LedgerAccount_.ledger),
			root.get(LedgerAccount_.type),
			cb.count(entry.get(LedgerEntry_.id))
		);
		
		cq.groupBy(
			root.get(LedgerAccount_.code),
			root.get(LedgerAccount_.ledger),
			root.get(LedgerAccount_.type)
		);
		
		cq.distinct(true);
	}

	public Long getCreditTotal() {
		return type == LedgerType.Credit ? total : 0;
	}
	
	public Long getDebitTotal() {
		return type == LedgerType.Debit ? total : 0;
	}
}
