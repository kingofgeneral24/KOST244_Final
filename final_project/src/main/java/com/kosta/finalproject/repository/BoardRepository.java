package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	// -----------검색----------

    int updateCount(Long id);
	@Modifying
	@Query("update Board p set p.count = p.count + 1 where p.id = :id")
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

	
	
	// 함수 위에 @Repository도 만들었다
//	Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
	
//	@Query(value="SELECT b FROM board b where b.title LIKE %:keyword% OF b.content LIKE %:keyword% OR b.author LIKE %:keyword%")
//	List<Board>findAllSearch(String keyword);
	
	
	
//	Page<Board> findAll(Pageable pageable);
	
	
	
//	@Query(value="SELECT *\r\n"
//			+ "FROM (\r\n"
//			+ "  SELECT rownum r, a.*\r\n"
//			+ "  FROM (SELECT * \r\n"
//			+ "          FROM test_board \r\n"
//			+ "          START WITH board_parent_no = 0\r\n"
//			+ "          CONNECT BY PRIOR board_no = board_parent_no\r\n"
//			+ "          ORDER SIBLINGS BY board_no DESC\r\n"
//			+ "  ) a\r\n"
//			+ "WHERE board_status=?3)\r\n"
//			+ "WHERE r BETWEEN ?1 AND ?2",
//			nativeQuery = true)
//	Page<Board> findAllSearch(String title, String content, Pageable pageable);
	// ------------------------

//	@Override
//	public List<Board> selectSearchList(Board board) throws Exception{
//		return sqlSession.selectList("Board.selectSearchList", board);
//	}
//	List<Board> selectSearchList(Board board);
}
