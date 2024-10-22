package com.jdc.balance;

import java.util.Arrays;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class LinksAggregator implements ArgumentsAggregator {

	@Override
	public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
			throws ArgumentsAggregationException {
		var lastArgs = accessor.getString(2);
		var array = lastArgs.split(",");
		return Arrays.stream(array).map(a -> a.trim()).map(Integer::parseInt).toList();
	}

}
