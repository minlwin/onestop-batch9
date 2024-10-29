package com.jdc.balance.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.input.LedgerEntryForm;
import com.jdc.balance.api.input.LedgerEntrySearch;
import com.jdc.balance.api.output.DataModificationResult;
import com.jdc.balance.api.output.LedgerEntryDetails;
import com.jdc.balance.api.output.LedgerEntryInfo;
import com.jdc.balance.api.output.PageInfo;
import com.jdc.balance.exceptions.ApiBusinessException;
import com.jdc.balance.model.LedgerEntrySeqGenerator;
import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccountPk;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryItem;
import com.jdc.balance.model.entity.LedgerEntryItemPk;
import com.jdc.balance.model.entity.LedgerEntryPk;
import com.jdc.balance.model.entity.LedgerEntryPk_;
import com.jdc.balance.model.entity.LedgerEntry_;
import com.jdc.balance.model.repo.LedgerAccountRepo;
import com.jdc.balance.model.repo.LedgerEntryItemRepo;
import com.jdc.balance.model.repo.LedgerEntryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerEntryService {
	
	private final LoginUserService loginUserService;
	private final LedgerAccountRepo ledgerRepo;

	private final LedgerEntryRepo entryRepo;
	private final LedgerEntryItemRepo itemRepo;
	
	private final LedgerEntrySeqGenerator seqGenerator;

	public PageInfo<LedgerEntryInfo> search(LedgerEntrySearch search, int page, int size) {
		
		var loginUser = loginUserService.getLoginUser();
		
		Function<CriteriaBuilder, CriteriaQuery<LedgerEntryInfo>> queryFunc = cb -> {
			var cq = cb.createQuery(LedgerEntryInfo.class);
			var root = cq.from(LedgerEntry.class);
			
			LedgerEntryInfo.select(cb, cq, root);
			cq.where(search.where(cb, root, loginUser.getEmail()));
			
			cq.orderBy(
				cb.desc(root.get(LedgerEntry_.id).get(LedgerEntryPk_.entryDate)),
				cb.desc(root.get(LedgerEntry_.id).get(LedgerEntryPk_.seqNumber))
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

	public LedgerEntryDetails findById(String trxId) {
		
		var loginUser = loginUserService.getLoginUser();
		var id = LedgerEntryPk.from(loginUser, trxId);
		
		return entryRepo.findById(id).map(LedgerEntryDetails::from)
				.orElseThrow(() -> new ApiBusinessException("There is no entry with trx id %s.".formatted(trxId)));
	}

	@Transactional
	public DataModificationResult<LedgerEntryPk> create(LedgerEntryForm form) {
		
		var loginUser = loginUserService.getLoginUser();
		var ledger = ledgerRepo.findById(LedgerAccountPk.from(loginUser, form.ledgerCode()))
				.orElseThrow(() -> new ApiBusinessException("There is no ledger with code %s.".formatted(form.ledgerCode())));
		
		
		if(!ledger.getAccount().getEmail().equals(loginUser.getEmail())) {
			throw new ApiBusinessException("You have no permission to use this ledger code.");
		}
		
		var entry = new LedgerEntry();
		entry.setId(seqGenerator.next(loginUser.getEmail()));
		
		entry.setIssueAt(LocalDate.now());
		entry.setParticular(form.particular());
		entry.setLedger(ledger);
		
		var total = form.items().stream()
				.mapToInt(a -> a.unitPrice() * a.quantity()).sum();
		
		var lastBalance = loginUser.getBalance().getAmount();
		
		var updatedBalance = ledger.getId().getType() == LedgerType.Credit ? 
				lastBalance.add(new BigDecimal(total)) : 
				lastBalance.subtract(new BigDecimal(total));
		
		entry.setLastBalance(loginUser.getBalance().getAmount());
		entry.setTotalAmount(new BigDecimal(total));
		
		loginUser.getBalance().setAmount(updatedBalance);
		
		entry = entryRepo.save(entry);
		
		for(var i = 0; i < form.items().size(); i ++) {
			
			var item = form.items().get(i);
			
			var itemEntity = new LedgerEntryItem();
			var itemEntityId = LedgerEntryItemPk.from(entry.getId(), i + 1);
			itemEntity.setId(itemEntityId);
			
			itemEntity.setEntry(entry);
			itemEntity.setEntryInfo(item.itemInfo());
			itemEntity.setUnitPrice(new BigDecimal(item.unitPrice()));
			itemEntity.setQuantity(item.quantity());
			
			itemRepo.save(itemEntity);
		}
		
		return DataModificationResult.success(entry.getId(), "Ledger entry is created successfully!");
	}

}
