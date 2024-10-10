package com.jdc.balance.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LedgerEntrySeq {

	private LedgerEntrySeqPk id;

	private int seqNumber;

}