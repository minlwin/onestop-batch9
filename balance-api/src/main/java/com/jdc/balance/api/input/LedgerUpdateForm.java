package com.jdc.balance.api.input;

import jakarta.validation.constraints.NotEmpty;

public record LedgerUpdateForm(
		@NotEmpty(message = "Please enter ledger account name.") String accountName) {

}
