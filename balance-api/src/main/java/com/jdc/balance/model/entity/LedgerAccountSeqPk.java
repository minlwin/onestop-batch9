package com.jdc.balance.model.entity;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountSeqPk {
	
	@Column(name = "account_id")
	private String accountId;
	private LedgerType type;
}
