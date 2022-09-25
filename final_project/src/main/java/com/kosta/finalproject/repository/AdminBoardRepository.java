package com.kosta.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.AdminBoard;

import java.util.List;
public class AdminBoardRepository {

	public static Page<AdminBoard> findByTitleContainingOrContentContaining(String searchText, String searchText2,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


	public interface AdminBoardRepository extends JpaRepository<Board, Long> {

	    List<Board> findByTitle(String title);
	    List<Board> findByTitleOrContent(String title, String content);

	    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

	}
