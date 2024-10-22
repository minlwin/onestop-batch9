package com.jdc.balance.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryItem;
import com.jdc.balance.model.entity.LedgerEntryItemPk;
import com.jdc.balance.model.entity.LedgerEntryPk;
import com.jdc.balance.model.repo.LedgerAccountRepo;
import com.jdc.balance.model.repo.LedgerEntryItemRepo;
import com.jdc.balance.model.repo.LedgerEntryRepo;

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
		// TODO Auto-generated method stub
		return null;
	}

	public LedgerEntryDetails findById(String trxId) {
		
		var loginUser = loginUserService.getLoginUser();
		var id = LedgerEntryPk.from(loginUser, trxId);
		
		return entryRepo.findById(id).map(LedgerEntryDetails::from)
				.orElseThrow(() -> new ApiBusinessException("There is no entry with trx id %s.".formatted(trxId)));
	}

	@Transactional
	public DataModificationResult<LedgerEntryPk> create(LedgerEntryForm form) {
		
		var ledger = ledgerRepo.findById(form.ledgerCode())
				.orElseThrow(() -> new ApiBusinessException("There is no ledger with code %s.".formatted(form.ledgerCode())));
		
		var loginUser = loginUserService.getLoginUser();
		
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
		
		var updatedBalance = ledger.getType() == LedgerType.Credit ? 
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
