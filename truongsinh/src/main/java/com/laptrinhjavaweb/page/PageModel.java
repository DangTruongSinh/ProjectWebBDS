package com.laptrinhjavaweb.page;

public class PageModel {
	private int offset;
	private int limit;
	private int page;

	public PageModel(int limit, int page) {
		this.limit = limit;
		this.page = page;
	}
	public int getOffset() {
		return offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
		this.offset = (this.page-1)*this.limit;
	}
	
}
