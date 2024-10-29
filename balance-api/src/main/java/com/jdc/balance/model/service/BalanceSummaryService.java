package com.jdc.balance.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jdc.balance.api.output.SummaryChartData;
import com.jdc.balance.api.output.SummaryData;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccountPk_;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryPk_;
import com.jdc.balance.model.entity.LedgerEntry_;
import com.jdc.balance.model.repo.LedgerEntryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceSummaryService {
	
	private final LoginUserService loginUserService;
	private final LedgerEntryRepo entryRepo;
	
	public SummaryData load() {
		
		var loginUser = loginUserService.getLoginUser();
		var result = new SummaryData();
		
		result.setLastCredit(search(loginUser.getEmail(), LedgerType.Credit, LocalDate.now().withDayOfMonth(1), null));
		result.setLastDebit(search(loginUser.getEmail(), LedgerType.Debit, LocalDate.now().withDayOfMonth(1), null));
		result.setTotalCredit(search(loginUser.getEmail(), LedgerType.Credit, null, null));
		result.setTotalDebit(search(loginUser.getEmail(), LedgerType.Debit, null, null));
		
		var chartData = new ArrayList<SummaryChartData>();
		result.setChartData(chartData);
		
		var startDate = LocalDate.now().minusDays(7);
		
		while(LocalDate.now().compareTo(startDate) > 0) {
			
			startDate = startDate.plusDays(1);

			var debit = search(loginUser.getEmail(), LedgerType.Debit, startDate, startDate);
			var credit = search(loginUser.getEmail(), LedgerType.Credit, startDate, startDate);
			
			chartData.add(new SummaryChartData(startDate, debit, credit));
		}
		
		return result;
	}
	
	
	private BigDecimal search(String loginId, LedgerType type, LocalDate from, LocalDate to) {
		
		Function<CriteriaBuilder, CriteriaQuery<BigDecimal>> func = cb -> {
			
			var cq = cb.createQuery(BigDecimal.class);
			var root = cq.from(LedgerEntry.class);
			
			var params = new ArrayList<Predicate>();
			params.add(cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.accountId),loginId));
			params.add(cb.equal(root.get(LedgerEntry_.ledger).get(LedgerAccount_.id).get(LedgerAccountPk_.type), type));
			
			if(null != from) {
				params.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.id).get(LedgerEntryPk_.entryDate), from));
			}
			
			if(null != to) {
				params.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.id).get(LedgerEntryPk_.entryDate), to));
			}

			cq.select(cb.sum(root.get(LedgerEntry_.totalAmount)));
			cq.where(params.toArray(size -> new Predicate[size]));
			
			return cq;
		};
		
		return entryRepo.searchOne(func).orElse(BigDecimal.ZERO);
	}

}
