package com.kosta.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;


//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
@Controller //
@RequestMapping("/board")
public class BoardController {
	
	@Autowired 
	private BoardRepository BoardRepository;
	
	
	@GetMapping("/findcarlist")
	public String list(Model model) {
		//model에 원하는 값을 넘겨주면됨
		List<Board> boards = BoardRepository.findAll();
		//List<Board> boards = BoardRepository.findStatus2();
		model.addAttribute("boards",boards);
		return "/board/findcarlist";
	}
	
	// 글 쓰기 및 글 수정
	@GetMapping("/findcarform")
	public String form(Model model, @RequestParam(required = false) Long boardNo,
						HttpSession session){

		if(boardNo==null) { //null일 경우 새 보드를 생성해서 타임리프에 넘겨줌
			Board board = new Board();
			//로그인 세션 유지해서 글 쓸때 ID 자동으로 입력되게함
			MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");
			board.setMemberId(memberDto.getMemberId());
			board.setBoardStatus(2);
			model.addAttribute("board",board);
		}else {//id가 값이 있을 경우 보드레파지에서 조회해서 넘겨줌
			Board board = BoardRepository.findByboardNo(boardNo);//.orElse(null);
			model.addAttribute("board", board);
		}
		
		return "/board/findcarform";
	}
	
	
	@PostMapping("/findcarform")
	public String formSubmit(@Validated Board board, BindingResult bindingResult) {
	//유효성 검사 어노테이션
		board.setBoardStatus(2);
		BoardRepository.save(board);
		return "redirect:/board/findcarlist";
		//redirect로 페이지 이동함
	}
	
	@Transactional
	@GetMapping("/findcardelete")
    public String boardDelete(Model model, Integer boardNo){
		BoardRepository.deleteByboardNo(Long.valueOf(boardNo));
		return "redirect:/board/findcarlist";
    }

}
