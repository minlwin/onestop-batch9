package com.jdc.balance.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;
import com.jdc.balance.model.entity.LedgerAccountPk;
import com.jdc.balance.model.entity.LedgerAccountSeq;
import com.jdc.balance.model.entity.LedgerAccountSeqPk;
import com.jdc.balance.model.repo.LedgerAccountSeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerAccountSeqGenerator {

	private final LedgerAccountSeqRepo repo;
	
	@Transactional
	public LedgerAccountPk next(String loginId, LedgerType type) {
		
		var id = new LedgerAccountSeqPk(loginId, type);
		
		var seq = repo.findById(id).orElseGet(() -> {
			var entity = new LedgerAccountSeq();
			entity.setId(id);
			return repo.save(entity);
		});

		seq.increment();
		
		return LedgerAccountPk.from(seq);
	}
}
