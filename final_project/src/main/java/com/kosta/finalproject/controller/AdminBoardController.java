package com.kosta.finalproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.kosta.finalproject.model.AdminBoard;
import com.kosta.finalproject.repository.AdminBoardRepository;
import com.kosta.finalproject.service.AdminBoardService;
import com.kosta.finalproject.validator.AdminBoardValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")

public class AdminBoardController {
	 @Autowired
	    private AdminBoardRepository boardRepository;

	    @Autowired
	    private AdminBoardService boardService;

	    @Autowired
	    private AdminBoardValidator boardValidator;

	    @GetMapping("/list")
	    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
	                       @RequestParam(required = false, defaultValue = "") String searchText) {

	        Page<AdminBoard> boards = AdminBoardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
	        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
	        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
	        model.addAttribute("startPage", startPage);
	        model.addAttribute("endPage", endPage);
	        model.addAttribute("boards", boards);
	        return "board/list";
	    }

	    @GetMapping("/form")
	    public String form(Model model, @RequestParam(required = false) Long id) {
	        if(id == null) {
	            model.addAttribute("board", new AdminBoard());
	        } else {
	            AdminBoard board = AdminBoardRepository.findById(id).orElse(null);
	            model.addAttribute("board", board);
	        }
	        return "board/form";
	    }

	    @PostMapping("/form")
	    public String postForm(@Valid AdminBoard board, BindingResult bindingResult, Authentication authentication) {
	        AdminboardValidator.validate(board, bindingResult);
	        if (bindingResult.hasErrors()) {
	            return "board/form";
	        }
	        String username = authentication.getName();
	        AdminboardService.save(username, board);
	        
	        return "redirect:/board/list";
	    }

	}


