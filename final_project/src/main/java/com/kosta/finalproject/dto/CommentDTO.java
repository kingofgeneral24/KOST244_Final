package com.kosta.finalproject.dto;


import java.sql.Date;
import java.time.LocalDateTime;

import com.kosta.finalproject.entity.CommentEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommentDTO {
	private Long commentNo;
	private int boardNo;
	private int parentNo;
	private String memberId;
	private int commentDepth;
	private String commentContent;
	private LocalDateTime commentRegdt;
		
		public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setCommentNo(commentEntity.getCommentNo());
			commentDTO.setBoardNo(commentEntity.getBoardNo());
			commentDTO.setParentNo(commentEntity.getParentNo());
			commentDTO.setMemberId(commentEntity.getMemberId());
			commentDTO.setCommentDepth(commentEntity.getCommentDepth());
			commentDTO.setCommentContent(commentEntity.getCommentContent());
			commentDTO.setCommentRegdt(commentEntity.getCommentRegdt());
			return commentDTO;
	
		}
}


