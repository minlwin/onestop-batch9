package com.jdc.balance.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.input.LedgerEditForm;
import com.jdc.balance.api.input.LedgerSearch;
import com.jdc.balance.api.input.LedgerUpdateForm;
import com.jdc.balance.api.output.DataModificationResult;
import com.jdc.balance.api.output.LedgerInfo;
import com.jdc.balance.api.output.LedgerListItem;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.exceptions.ApiBusinessException;
import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.repo.LedgerAccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerService {
	
	private final LedgerAccountRepo ledgerAccountRepo;
	private final LoginUserService loginUserService;

	public PageInfo<LedgerListItem> search(LedgerSearch search, int page, int size) {
		return PageInfo.from(ledgerAccountRepo.search(
				cb -> {
					var cq = cb.createQuery(LedgerListItem.class);
					var root = cq.from(LedgerAccount.class);
					LedgerListItem.select(cb, cq, root);
					cq.where(search.where(cb, root, loginUserService.getLoginUser()));
					cq.orderBy(cb.asc(root.get(LedgerAccount_.code)));
					return cq;
				}, 
				cb -> {
					var cq = cb.createQuery(Long.class);
					var root = cq.from(LedgerAccount.class);
					cq.select(cb.count(root.get(LedgerAccount_.code)));
					cq.where(search.where(cb, root, loginUserService.getLoginUser()));
					return cq;
				}, 
				page, size));
	}

	public LedgerInfo findById(String code) {
		return ledgerAccountRepo.findById(code)
				.map(LedgerInfo::from)
				.orElseThrow(() -> new ApiBusinessException("There is no ledger with code %s.".formatted(code)));
	}

	@Transactional
	public DataModificationResult<String> create(LedgerEditForm form) {
		
		var entity = form.entity();
		entity.setAccount(loginUserService.getLoginUser());
		
		ledgerAccountRepo.save(entity);
		
		return DataModificationResult.success(entity.getCode(), "Ledger account is created successfully!");
	}

	@Transactional
	public DataModificationResult<String> update(String code, LedgerUpdateForm form) {
		
		var entity = ledgerAccountRepo.findById(code)
				.orElseThrow(() -> new ApiBusinessException("There is no ledger with code %s.".formatted(code)));
		
		var loginUser = loginUserService.getLoginUser();
		
		if(!entity.getAccount().getEmail().equals(loginUser.getEmail())) {
			throw new ApiBusinessException("You have no permission to update this ledger.");
		}
		
		entity.setLedger(form.accountName());
		
		return DataModificationResult.success(entity.getCode(), "Ledger account is updated successfully!");
	}

}
