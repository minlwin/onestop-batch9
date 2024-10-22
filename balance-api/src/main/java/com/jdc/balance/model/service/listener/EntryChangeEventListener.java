package com.jdc.balance.model.service.listener;

import java.time.LocalDate;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.model.entity.AccountActivity;
import com.jdc.balance.model.entity.LedgerEntry;
import com.jdc.balance.model.entity.LedgerEntryPk_;
import com.jdc.balance.model.entity.LedgerEntry_;
import com.jdc.balance.model.repo.AccountActivityRepo;
import com.jdc.balance.model.repo.AccountRepo;
import com.jdc.balance.model.repo.LedgerEntryRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntryChangeEventListener {

	private final AccountRepo accountRepo;
	private final AccountActivityRepo activityRepo;
	private final LedgerEntryRepo entryRepo;
	
	@Async
	@EventListener
	@Transactional
	public void handle(EntryChangeEvent event) {
		
		accountRepo.findById(event.id().getAccountId()).ifPresent(account -> {
			var totalCount = findTotalEntry(event.id().getAccountId());
			var monthlyCount = findMonthlyEntry(event.id().getAccountId());
			
			var activity = account.getActivity();
			if(null == activity) {
				activity = new AccountActivity();
				activity.setAccount(account);
				activity.setAccountId(account.getEmail());
				activity = activityRepo.save(activity);
			}
			
			activity.setTotalEntries(totalCount.intValue());
			activity.setLastMonthEntries(monthlyCount.intValue());
		});
	}

	private Long findMonthlyEntry(String accountId) {
		return entryRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(
				cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.accountId), accountId),
				cb.greaterThanOrEqualTo(root.get(LedgerEntry_.id).get(LedgerEntryPk_.entryDate), LocalDate.now().withDayOfMonth(1))
			);
			return cq;
		}).orElse(0L);
	}

	private Long findTotalEntry(String accountId) {
		return entryRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.accountId), accountId));
			return cq;
		}).orElse(0L);
	}
}
