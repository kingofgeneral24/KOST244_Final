package com.kosta.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.Board;



public interface BoardRepository  extends JpaRepository<Board, Integer> {

	Board findByboardNo(Long boardNo);

	void deleteByboardNo(Long boardNo);

	
}
