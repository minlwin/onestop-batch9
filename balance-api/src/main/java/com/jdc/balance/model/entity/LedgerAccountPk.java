package com.jdc.balance.model.entity;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerAccountPk {

	@Column(name = "account_id")
	private String accountId;
	private LedgerType type;
	private int seqNumber;
	
	public String getCode() {
		return "%s%04d".formatted(type.name().toCharArray()[0], seqNumber);
	}

	public static LedgerAccountPk from(LedgerAccountSeq seq) {
		
		var id = new LedgerAccountPk();
		id.setAccountId(seq.getId().getAccountId());
		id.setType(seq.getId().getType());
		id.setSeqNumber(seq.getSeqNum());
		
		return id;
	}

	public static LedgerAccountPk from(Account loginUser, String code) {
		var id = new LedgerAccountPk();
		
		id.setAccountId(loginUser.getEmail());
		id.setType(LedgerType.from(code.toCharArray()[0]));
		id.setSeqNumber(Integer.parseInt(code.substring(1)));
		
		return id;
	}
}
