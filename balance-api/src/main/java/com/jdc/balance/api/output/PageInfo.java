package com.jdc.balance.api.output;

import java.util.List;

public record PageInfo<T>(
		List<T> contents,
		int totalPage,
		int totalCount,
		int currentPage,
		int pageSize,
		List<Integer> links
		) {

}
