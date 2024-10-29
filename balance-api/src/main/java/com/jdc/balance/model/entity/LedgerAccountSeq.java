package com.jdc.balance.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class LedgerAccountSeq {

	@EmbeddedId
	private LedgerAccountSeqPk id;
	private int seqNum;
	
	public void increment() {
		seqNum ++;
	}
}
