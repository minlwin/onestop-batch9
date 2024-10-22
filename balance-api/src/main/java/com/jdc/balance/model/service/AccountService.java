package com.jdc.balance.model.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jdc.balance.api.input.AccountSearch;
import com.jdc.balance.api.output.AccountInfo;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account_;
import com.jdc.balance.model.repo.AccountRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountRepo repo;

	public PageInfo<AccountInfo> search(AccountSearch search, int page, int size) {
		return PageInfo.from(repo.search(queryFunc(search), countFunc(search), page, size));
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<AccountInfo>> queryFunc(AccountSearch search) {
		return cb -> {
			var cq = cb.createQuery(AccountInfo.class);
			var root = cq.from(Account.class);
			
			AccountInfo.select(cq, root);
			cq.distinct(true);
			cq.where(search.where(cb, root));
			cq.orderBy(cb.desc(root.get(Account_.entryAt)));
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(AccountSearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Account.class);
			
			cq.select(cb.count(root.get(Account_.email)));
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}
	

}
