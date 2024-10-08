package com.jdc.balance.api.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record LedgerEntryFormItem(
		@NotEmpty(message = "Please enter entry item information.") String itemInfo,
		@Min(value = 100, message = "Please enter unit price.") int unitPrice,
		@Min(value = 1, message = "Please enter quantity.") int quantity) {

}
