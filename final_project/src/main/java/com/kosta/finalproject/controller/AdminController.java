package com.kosta.finalproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.finalproject.service.BoardService;

public class AdminController {
	private BoardService boardService;
	
	/*게시글 목록*/
	@GetMapping("/")
	public String list(Model model) {
		List<BoardDTO>boardList = boardService.getBoardlist();
		
		model.addAttribute("boardList", boardList);
		return "adminlist.html";
	}

	@GetMapping("/")
	public String detail(@PAthVariable("no") Long no, Model model) {
		BoardDTO boardDTO = boardService.getPost(no);
		
		model.addAttribute("boardDTO". boardDTO);
		return "board/adminForm.html";
		
		
	}
}
