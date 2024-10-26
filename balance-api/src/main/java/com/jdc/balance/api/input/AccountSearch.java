package com.jdc.balance.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account.Role;
import com.jdc.balance.model.entity.AccountActivity_;
import com.jdc.balance.model.entity.Account_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record AccountSearch(
		LocalDate entryFrom,
		LocalDate entryTo,
		LocalDate lastAccessFrom,
		LocalDate lastAccessTo,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Account> root) {
		
		var list = new ArrayList<Predicate>();
		
		list.add(cb.equal(root.get(Account_.role), Role.Member));
		
		var activity = root.join(Account_.activity, JoinType.LEFT);
		
		if(null != entryFrom) {
			list.add(cb.greaterThanOrEqualTo(root.get(Account_.entryAt), entryFrom.atStartOfDay()));
		}
		
		if(null != entryTo) {
			list.add(cb.lessThan(root.get(Account_.entryAt), entryTo.plusDays(1).atStartOfDay()));
		}

		if(null != lastAccessFrom) {
			list.add(cb.greaterThanOrEqualTo(activity.get(AccountActivity_.lastAccess), lastAccessFrom.atStartOfDay()));
		}

		if(null != lastAccessTo) {
			list.add(cb.greaterThanOrEqualTo(activity.get(AccountActivity_.lastAccess), lastAccessTo.plusDays(1).atStartOfDay()));
		}
		
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.or(
				cb.like(cb.lower(root.get(Account_.name)), keyword.toLowerCase().concat("%")),
				cb.like(cb.lower(root.get(Account_.email)), keyword.toLowerCase().concat("%"))
			));
		}

		return list.toArray(size -> new Predicate[size]);
	}
}
