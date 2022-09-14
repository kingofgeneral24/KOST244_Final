package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.finalproject.model.Board;



public interface BoardRepository  extends JpaRepository<Board, Long> {

	Board findByboardNo(Long boardNo);
	
//	@Modifying
//	@Query(value = "SELECT * \r\n"
//			+ "FROM BOARD\r\n"
//			+ "WHERE board_status=?1", 
//			nativeQuery = true)
	List<Board> findByboardStatus(int status);

	@Query(value="SELECT *\r\n"
			+ "FROM (\r\n"
			+ "  SELECT rownum r, a.*\r\n"
			+ "  FROM (SELECT * \r\n"
			+ "          FROM test_board \r\n"
			+ "          START WITH board_parent_no = 0\r\n"
			+ "          CONNECT BY PRIOR board_no = board_parent_no\r\n"
			+ "          ORDER SIBLINGS BY board_no DESC\r\n"
			+ "  ) a\r\n"
			+ "WHERE board_status=?3)\r\n"
			+ "WHERE r BETWEEN ?1 AND ?2",
			nativeQuery = true)
	List<Board> findByPage(int startRow, int endRow, int status);
}
