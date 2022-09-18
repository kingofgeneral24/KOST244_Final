package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.Board;



public interface BoardRepository  extends JpaRepository<Board, Integer> {

	Board findByboardNo(Long boardNo);

	void deleteByboardNo(Long boardNo);

	List<Board> findByboardStatus(int status);
	
	Page<Board> findByboardTitleContaining(String searchKeyword, Pageable pageable);
	
	Page<Board> findByboardStatus(int status, Pageable pageable);

}