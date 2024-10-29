package com.jdc.balance.model.service.listener;

import com.jdc.balance.model.entity.LedgerAccountPk;

public record LedgerChangesEvent(LedgerAccountPk id) {

}
