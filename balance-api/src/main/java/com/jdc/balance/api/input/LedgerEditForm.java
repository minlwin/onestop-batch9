package com.jdc.balance.api.input;

import com.jdc.balance.model.entity.LedgerAccount.LedgerType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LedgerEditForm(
		@NotNull(message = "Please select ledger type.") LedgerType type,
		@NotEmpty(message = "Please enter ledger account name.") String accountName) {

}
