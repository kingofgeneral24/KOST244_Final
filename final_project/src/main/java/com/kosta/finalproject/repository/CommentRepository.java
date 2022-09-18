package com.kosta.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	
	/*	order by 키워드로 계속 조건을 줄 수 있음
	select * from comment_table
	order by parent_no asc, comment_depth asc, comment_regdt asc;
	*/
	List<CommentEntity> findByBoardNoOrderByParentNoAscCommentDepthAscCommentRegdtAsc(int boardNo);
	
}