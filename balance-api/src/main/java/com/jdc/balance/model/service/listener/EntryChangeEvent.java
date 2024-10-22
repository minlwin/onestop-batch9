package com.jdc.balance.model.service.listener;

import com.jdc.balance.model.entity.LedgerEntryPk;

public record EntryChangeEvent(LedgerEntryPk id) {

}
