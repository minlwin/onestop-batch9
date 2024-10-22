package com.jdc.balance.api.output;

import java.util.function.Function;

public record DataModificationResult<ID>(
		ID id,
		String message
		) {
	
	public<R>  DataModificationResult<R> map(Function<ID, R> func) {
		return new DataModificationResult<R>(func.apply(id), message);
	}

	public static<T> DataModificationResult<T> success(T id, String message) {
		return new DataModificationResult<T>(id, message);
	}
}
