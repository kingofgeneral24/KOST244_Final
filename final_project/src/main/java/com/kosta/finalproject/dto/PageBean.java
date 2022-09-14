package com.kosta.finalproject.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@ToString
public class PageBean<T> {
	private List<T> list;
	private int currentPage;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int cntPerPageGroup = 2;
	
	public PageBean(List<T>list, Long totalCnt, int currentPage, int cntPerPageGroup, int cntPerPage) {
		this.list = list;
		this.currentPage = currentPage;
		this.cntPerPageGroup = cntPerPageGroup;
		
		this.totalPage = (int)Math.ceil((double)totalCnt/cntPerPage);
		this.endPage = (int)(Math.ceil((double)currentPage/cntPerPageGroup)*cntPerPageGroup);
		this.startPage = this.endPage - cntPerPageGroup + 1;
		if(this.totalPage < this.endPage) {
			this.endPage = this.totalPage;
		}
	}
}
