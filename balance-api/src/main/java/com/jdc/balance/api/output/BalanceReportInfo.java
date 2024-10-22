package com.jdc.balance.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntry_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record BalanceReportInfo(
		LocalDate issueAt,
		String ledgerCode,
		String ledgerAccountName,
		LedgerType type,
		String particular,
		BigDecimal amount,
		BigDecimal lastBalance) {
	
	public static void select(CriteriaQuery<BalanceReportInfo> cq, Root<LedgerEntry> root) {
		cq.multiselect(
			root.get(LedgerEntry_.issueAt),
			root.get(LedgerEntry_.ledger).get(LedgerAccount_.code),
			root.get(LedgerEntry_.ledger).get(LedgerAccount_.ledger),
			root.get(LedgerEntry_.ledger).get(LedgerAccount_.type),
			root.get(LedgerEntry_.particular),
			root.get(LedgerEntry_.totalAmount),
			root.get(LedgerEntry_.lastBalance)
		);
	}

	public BigDecimal getDebit() {
		return type == LedgerType.Debit ? amount : BigDecimal.ZERO;
	}

	public BigDecimal getCredit() {
		return type == LedgerType.Credit ? amount : BigDecimal.ZERO;
	}
	
	public BigDecimal getBalance() {
		return type == LedgerType.Credit ? lastBalance.add(amount) : lastBalance.subtract(amount);
	}
}
