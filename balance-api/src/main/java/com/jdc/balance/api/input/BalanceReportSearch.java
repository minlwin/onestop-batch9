package com.jdc.balance.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryPk_;
import com.jdc.balance.model.entity.LedgerEntry_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record BalanceReportSearch(
		LocalDate from,
		LocalDate to,
		String ledger) {

	public Predicate[] where(CriteriaBuilder cb, Root<LedgerEntry> root, String accountId) {
		var list = new ArrayList<Predicate>();
		list.add(cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.accountId), accountId));
		
		if(null != from) {
			list.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), from));
		}
		
		if(null != to) {
			list.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.issueAt), to));
		}
		
		if(StringUtils.hasLength(ledger)) {
			list.add(cb.or(
				cb.like(cb.lower(root.get(LedgerEntry_.ledger).get(LedgerAccount_.code)), ledger.toLowerCase().concat("%")),
				cb.like(cb.lower(root.get(LedgerEntry_.ledger).get(LedgerAccount_.ledger)), ledger.toLowerCase().concat("%"))
			));
		}

		return list.toArray(size -> new Predicate[size]);
	}
}
