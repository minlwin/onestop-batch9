package com.jdc.balance.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.model.entity.LedgerEntryPk;
import com.jdc.balance.model.entity.LedgerEntrySeq;
import com.jdc.balance.model.entity.LedgerEntrySeqPk;
import com.jdc.balance.model.repo.LedgerEntrySeqRepo;

@Service
public class LedgerEntrySeqGenerator {
	
	@Autowired
	private LedgerEntrySeqRepo repo;

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public LedgerEntryPk next(String accountId) {
		
		var id = LedgerEntrySeqPk.now(accountId);
		
		var seq = repo.findById(id).orElseGet(() -> {
			var entity = new LedgerEntrySeq();
			entity.setId(id);
			return repo.save(entity);
		});
		
		seq.increment();
		
		return LedgerEntryPk.from(seq);
	}
}
