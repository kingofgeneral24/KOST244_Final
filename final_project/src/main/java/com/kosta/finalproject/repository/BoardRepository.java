package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kosta.finalproject.model.Board;



public interface BoardRepository  extends JpaRepository<Board, Integer> {

	Board findByboardNo(Long boardNo);
	
//	@Modifying
//	@Query(value = "SELECT * \r\n"
//			+ "FROM BOARD\r\n"
//			+ "WHERE board_status=?1", 
//			nativeQuery = true)
	List<Board> findByboardStatus(int status);

	void deleteByboardNo(Long boardNo);
}
