package com.jdc.balance.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryItem_;
import com.jdc.balance.model.entity.LedgerEntryPk;
import com.jdc.balance.model.entity.LedgerEntry_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record LedgerEntryInfo(
		String trxId,
		LocalDate issueAt,
		String ledger,
		String particular,
		int items,
		BigDecimal amount
) {

	public LedgerEntryInfo(LedgerEntryPk id, LocalDate issueAt, String ledger, String particular, BigDecimal amount, Long count) {
		this(id.getCode(), issueAt, ledger, particular, count.intValue(), amount);
	}
	
	public static void select(CriteriaBuilder cb, CriteriaQuery<LedgerEntryInfo> cq, Root<LedgerEntry> root) {
		
		var items = root.join(LedgerEntry_.items, JoinType.LEFT);
		
		cq.multiselect(
			root.get(LedgerEntry_.id),
			root.get(LedgerEntry_.issueAt),
			root.get(LedgerEntry_.ledger).get(LedgerAccount_.ledger),
			root.get(LedgerEntry_.particular),
			root.get(LedgerEntry_.totalAmount),
			cb.count(items.get(LedgerEntryItem_.id))
		);
		
		cq.distinct(true);
		
		cq.groupBy(
			root.get(LedgerEntry_.id),
			root.get(LedgerEntry_.issueAt),
			root.get(LedgerEntry_.ledger).get(LedgerAccount_.ledger),
			root.get(LedgerEntry_.particular),
			root.get(LedgerEntry_.totalAmount)
		);
	}
}
