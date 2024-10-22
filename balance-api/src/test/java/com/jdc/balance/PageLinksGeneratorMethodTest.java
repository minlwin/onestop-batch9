package com.jdc.balance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.balance.model.utils.PageLinksFactory;

public class PageLinksGeneratorMethodTest {

	@ParameterizedTest
	@CsvSource(value = {
		"5	0	0, 1, 2, 3, 4",
		"2	1	0, 1",
		"10	7	5, 6, 7, 8, 9",
		"10	9	5, 6, 7, 8, 9",
		"10	6	4, 5, 6, 7, 8"			
	}, delimiter = '\t')
	void test(int total, int current, @AggregateWith(LinksAggregator.class) List<Integer> links) {
		
		var result = PageLinksFactory.getPageLink(total, current);
		System.out.println(result);
		assertNotNull(result);
		
		
		assertEquals(links.size(), result.size());
		
		for(var i = 0; i < links.size(); i ++) {
			assertEquals(links.get(i), result.get(i));
		}
	}
}
