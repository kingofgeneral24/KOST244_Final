package com.kosta.finalproject.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosta.finalproject.dto.CommentDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "comment_table")
public class CommentEntity {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_Sequence")
	@SequenceGenerator(name = "comment_Sequence", sequenceName = "COMMENT_SEQ", initialValue= 1, allocationSize= 1)
	@Column(name = "comment_no")
	/*코멘트 키*/
	private Long commentNo;
	
	/*댓글이 달린 게시판 키*/
	@Column(name="board_no")
	private int boardNo;
	
	/*댓글의 부모키 키*/	
	@Column(name="parent_no")
	private int parentNo;
	
	/*회원아이디 (작성자)*/
	@Column(name="member_id")
	private String memberId;
	
	/*댓글/대댓글 유무*/
	@Column(name="comment_depth")
	private int commentDepth;
	
	/*코멘트 내용*/
	@Column(name="comment_contnet", length = 255)
	private String commentContent;
	
	/*코멘트 작성일 키*/
	@Column(name="comment_regdt")
	private LocalDateTime commentRegdt;
	
	public static CommentEntity toSaveEntity(CommentDTO commentDTO) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentNo(commentDTO.getCommentNo());
		commentEntity.setBoardNo(commentDTO.getBoardNo());
		commentEntity.setParentNo(commentDTO.getParentNo());
		commentEntity.setMemberId(commentDTO.getMemberId());
		commentEntity.setCommentDepth(commentDTO.getCommentDepth());
		commentEntity.setCommentContent(commentDTO.getCommentContent());
		commentEntity.setCommentRegdt(commentDTO.getCommentRegdt());
		return commentEntity;
		
	
}
	
}


