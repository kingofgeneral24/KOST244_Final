package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.finalproject.model.Board;


//@Repository
public interface BoardRepository  extends JpaRepository<Board, Long> {

	Board findByboardNo(Long boardNo);
	
//	@Modifying
//	@Query(value = "SELECT * \r\n"
//			+ "FROM BOARD\r\n"
//			+ "WHERE board_status=?1", 
//			nativeQuery = true)
	List<Board> findByboardStatus(int status);
	

	Page<Board> findByboardTitleContaining(String searchKeyword, Pageable pageable);
	
}
