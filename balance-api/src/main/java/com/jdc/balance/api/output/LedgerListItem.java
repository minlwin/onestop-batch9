package com.jdc.balance.api.output;

import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccountPk;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record LedgerListItem(
		String code,
		LedgerType type,
		String accountName,
		long total) {
	
	public LedgerListItem(
			LedgerAccountPk id,
			String accountName,
			long total
			) {
		this(id.getCode(), id.getType(), accountName, total);
	}
	
	public static void select(CriteriaBuilder cb, CriteriaQuery<LedgerListItem> cq, Root<LedgerAccount> root) {
		
		var entry = root.join(LedgerAccount_.entry, JoinType.LEFT);
		
		cq.multiselect(
			root.get(LedgerAccount_.id),
			root.get(LedgerAccount_.ledger),
			cb.count(entry.get(LedgerEntry_.id))
		);
		
		cq.groupBy(
			root.get(LedgerAccount_.id),
			root.get(LedgerAccount_.ledger)
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
