package com.kosta.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;

public class BoardService {
	private static final int CNT_PER_PAGE = 10; //페이지별 보여줄 목록수
	@Autowired
	private BoardRepository repository;
	@Autowired
	private Board board;
	/**
	 * 페이지별 게시글 목록과 페이지그룹정보를 반환한다 
	 * @param currentPage 검색할 페이지
	 * @return 
	 * @throws FindException
	 */
	public PageBean<Board> boardList(int currentPage) throws FindException{
		int endRow = currentPage*CNT_PER_PAGE;
		int startRow = endRow - CNT_PER_PAGE + 1;
		List<Board> list =repository.findByPage(startRow, endRow, 2);  // board.boardStatus받아오는 방법?
		long totalCnt = repository.count();    // 총 행수 받아오기
		int cntPerPageGroup = 2;  // 페이지별 보여줄 페이지수
		PageBean<Board> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
		return pb1;
	}
}
