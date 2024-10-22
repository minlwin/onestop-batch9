package com.jdc.balance.model.service;

import com.jdc.balance.api.input.LedgerEditForm;
import com.jdc.balance.api.input.LedgerSearch;
import com.jdc.balance.api.output.DataModificationResult;
import com.jdc.balance.api.output.LedgerInfo;
import com.jdc.balance.api.output.LedgerListItem;
import com.jdc.balance.api.output.PageInfo;

public class LedgerService {

	public PageInfo<LedgerListItem> search(LedgerSearch search, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public LedgerInfo findById(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataModificationResult<String> create(LedgerEditForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataModificationResult<String> update(String code, LedgerEditForm form) {
		// TODO Auto-generated method stub
		return null;
	}

}
