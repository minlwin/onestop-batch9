package com.jdc.balance.model.utils;

import java.util.ArrayList;
import java.util.List;

public class PageLinksFactory {

	public static List<Integer> getPageLink(int totalPage, int current) {
		
		var list = new ArrayList<Integer>();
		list.add(current);
		
		while(list.size() < 3 && list.get(0) > 0) {
			list.add(0, list.get(0) - 1);
		}
		
		while(list.size() < 5 && list.get(list.size() - 1) < totalPage - 1) {
			list.add(list.get(list.size() - 1) + 1);
		}
		
		while(list.size() < 5 && list.get(0) > 0) {
			list.add(0, list.get(0) - 1);
		}
		
		return list;
	}
}
