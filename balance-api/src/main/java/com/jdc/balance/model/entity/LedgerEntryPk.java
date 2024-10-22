package com.jdc.balance.model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jdc.balance.exceptions.ApiBusinessException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntryPk {

	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Column(name = "account_id")
	private String accountId;
	@Column(name = "entry_date")
	private LocalDate entryDate;
	@Column(name = "seq_number")
	private int seqNumber;
	
	public static LedgerEntryPk from(LedgerEntrySeq seq) {
		return new LedgerEntryPk(seq.getId().getAccountId(), seq.getId().getEntryDate(), seq.getSeqNumber());
	}
	
	public static LedgerEntryPk from(Account loginUser, String trxId) {
		
		var id = new LedgerEntryPk();
		id.setAccountId(loginUser.getEmail());
		
		var array = trxId.split("-");
		
		if(array.length != 2) {
			throw new ApiBusinessException("Invalid transaction id.");
		}
		
		id.setEntryDate(LocalDate.parse(array[0], DF));
		id.setSeqNumber(Integer.parseInt(array[1]));
		
		return id;
	}

	public String getCode() {
		return "%s-%02d".formatted(entryDate.format(DF), seqNumber);
	}

	

}