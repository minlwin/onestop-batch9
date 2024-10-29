package com.jdc.balance.model.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jdc.balance.api.input.BalanceReportSearch;
import com.jdc.balance.api.output.BalanceReportInfo;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryPk_;
import com.jdc.balance.model.entity.LedgerEntry_;
import com.jdc.balance.model.repo.LedgerEntryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceReportService {

	private final LoginUserService loginUserService;
	private final LedgerEntryRepo entryRepo;

	public PageInfo<BalanceReportInfo> search(BalanceReportSearch search, int page, int size) {
		var loginUser = loginUserService.getLoginUser();
		
		Function<CriteriaBuilder, CriteriaQuery<BalanceReportInfo>> queryFunc = cb -> {
			var cq = cb.createQuery(BalanceReportInfo.class);
			var root = cq.from(LedgerEntry.class);
			
			BalanceReportInfo.select(cq, root);
			cq.where(search.where(cb, root, loginUser.getEmail()));
			
			cq.orderBy(
				cb.asc(root.get(LedgerEntry_.id).get(LedgerEntryPk_.entryDate)),
				cb.asc(root.get(LedgerEntry_.id).get(LedgerEntryPk_.seqNumber))
			);
			
			return cq;
		};
		
		Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc = cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(search.where(cb, root, loginUser.getEmail()));
			return cq;
		};

		return PageInfo.from(entryRepo.search(queryFunc, countFunc, page, size));
	}

}
