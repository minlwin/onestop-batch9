package com.jdc.balance.model.service.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.model.entity.Account;
import com.jdc.balance.model.entity.Account_;
import com.jdc.balance.model.entity.LedgerAccount;
import com.jdc.balance.model.entity.LedgerAccount_;
import com.jdc.balance.model.repo.LedgerAccountRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LedgerChangesEventListener {
	
	private final LedgerAccountRepo ledgerRepo;

	@Async
	@EventListener
	@Transactional
	public void handle(LedgerChangesEvent event) {
		
		ledgerRepo.findById(event.code()).ifPresent(ledger -> {
			var account = ledger.getAccount();
			Long ledgerCount = findCountByAccount(account);
			account.getActivity().setTotalLedgers(ledgerCount.intValue());
		});
	}

	private Long findCountByAccount(Account account) {
		return ledgerRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class);
			
			var root = cq.from(LedgerAccount.class);
	
			cq.select(cb.count(root.get(LedgerAccount_.code)));
			cq.where(cb.equal(root.get(LedgerAccount_.account).get(Account_.email), account.getEmail()));
			
			return cq;
		}).orElse(0L);
	}
}
