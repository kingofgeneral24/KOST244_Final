package com.kosta.finalproject.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.finalproject.dto.CommentDTO;
import com.kosta.finalproject.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")

public class CommentController {
	
	private final CommentService commentService;

	/*댓글 저장*/
	@PostMapping("/commentSave")
	@ResponseBody
	public String commentSave(CommentDTO commentDTO) {
		
		log.info("front에서 넘어온 commentSave commentDTO 값: "+commentDTO.toString());
        
        Long result = commentService.commentSave(commentDTO);
        
        return result.toString();
        
	}	

	/*댓글 목록조회*/
	@PostMapping("/commentList")
	@ResponseBody
	public List<CommentDTO> commentList(CommentDTO commentDTO) {
		
		log.info("front에서 넘어온 commentList commentDTO 값: "+commentDTO.toString());
        
        return commentService.commentList(commentDTO.getBoardNo());
        
	}	

	/*댓글 삭제*/
	@PostMapping("/commentDelete")
	@ResponseBody
	public void commentDelete(CommentDTO commentDTO) {
		log.info("front에서 넘어온 commentDelete commentDTO 값: "+commentDTO.toString());
        commentService.commentDelete(commentDTO.getCommentNo());
	}	
}