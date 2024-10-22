package com.jdc.balance.api.input;


import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account_;
import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccount_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record LedgerSearch(
		LedgerType type,
		String keyword
		) {

	public Predicate[] where(CriteriaBuilder cb, Root<LedgerAccount> root, Account loginUser) {
		var list = new ArrayList<Predicate>();
		list.add(cb.equal(root.get(LedgerAccount_.account).get(Account_.email), loginUser.getEmail()));
		
		if(null != type) {
			list.add(cb.equal(root.get(LedgerAccount_.type), type));
		}
		
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.like(cb.lower(root.get(LedgerAccount_.ledger)), keyword.toLowerCase().concat("%")));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
