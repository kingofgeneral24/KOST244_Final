package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.Board;



public interface BoardRepository  extends JpaRepository<Board, Long> {

	Board findByboardNo(Long boardNo);

	List<Board> findByboardStatus(int status);
	
	

	//void deleteByboardNo(Long boardNo);

	
	
}
