package com.jdc.balance.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LedgerEntrySeq {

	@EmbeddedId
	private LedgerEntrySeqPk id;

	private int seqNumber;

}