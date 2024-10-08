package com.jdc.balance.api.output;

public record DataModificationResult<ID>(
		ID id,
		String message
		) {

}
