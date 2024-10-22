package com.jdc.balance.api.output;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jdc.balance.model.utils.PageLinksFactory;

public record PageInfo<T>(
		List<T> contents,
		int totalPage,
		long totalCount,
		int currentPage,
		int pageSize,
		List<Integer> links
		) {

	public static<T> PageInfo<T> from(Page<T> page) {
		Builder<T> builder = builder();
		return builder
				.contents(page.getContent())
				.totalCount(page.getTotalElements())
				.currentPage(page.getNumber())
				.pageSize(page.getSize())
				.totalPage(page.getTotalPages())
				.links(PageLinksFactory.getPageLink(page.getTotalPages(), page.getNumber()))
				.build();
	}

	
	public static<T> Builder<T> builder() {
		return new Builder<T>();
	}
	
	public static class Builder<T> {
		private Builder() {
		}
		
		private List<T> contents;
		private int totalPage;
		private long totalCount;
		private int currentPage;
		private int pageSize;
		private List<Integer> links;

		public PageInfo<T> build() {
			return new PageInfo<T>(contents, totalPage, totalCount, currentPage, pageSize, links);
		}

		public Builder<T> contents(List<T> contents) {
			this.contents = contents;
			return this;
		}

		public Builder<T> totalPage(int totalPage) {
			this.totalPage = totalPage;
			return this;
		}

		public Builder<T> totalCount(long totalCount) {
			this.totalCount = totalCount;
			return this;
		}

		public Builder<T> currentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public Builder<T> pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder<T> links(List<Integer> links) {
			this.links = links;
			return this;
		}
	}
	
}
