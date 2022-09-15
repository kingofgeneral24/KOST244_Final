package com.kosta.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;
@Service
public class BoardService {
	private static final int CNT_PER_PAGE = 5; //페이지별 보여줄 목록수
	@Autowired
	private BoardRepository repository;
	
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
		int cntPerPageGroup = 5;  // 페이지별 보여줄 페이지수
		PageBean<Board> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
		return pb1;
	}
	
	public Page<Board> getList(int page){
		Pageable pageable = PageRequest.of(page, 10);
		return this.repository.findAll(pageable);
	}
	
	// --------------페이징--------------
	@Transactional(readOnly = true)
	public Page<Board> findAll(Pageable pageable){
		return repository.findAll(pageable);
	}
	// ---------------------------------
	
	// -------------검색-------------
	@Transactional(readOnly = true)
	public Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable){
		return repository.findByTitleContainingOrContentContaining(title, content, pageable);
	}
	
//	@Transactional
//	public List<Board> searchPosts(String keyword){
//		return repository.findAllSearch(keyword).stream()
//				.map(BoardListDto::new)
//				.collect(Collectiors.toList());
//	}
	
	
//	public List<Board> getSearchList(Board board) throws Exception{
//		return repository.selectSearchList(board);
//	}
	// -----------------------------

	//////////////////////////////////
//	public void write(Board board) {
//		repository.save(board);
//	}
//	public Page<Board> boardList(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	//////////////////////////////////
}
