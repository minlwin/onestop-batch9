package com.jdc.balance.api.output;

public record DataModificationResult<ID>(
		ID id,
		String message
		) {

	public static<T> DataModificationResult<T> success(T id, String message) {
		return new DataModificationResult<T>(id, message);
	}
}
