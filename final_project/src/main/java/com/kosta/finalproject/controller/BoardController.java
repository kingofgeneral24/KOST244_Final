package com.kosta.finalproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.dto.ResultBean;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;
import com.kosta.finalproject.repository.CarInfoRepository;
import com.kosta.finalproject.service.BoardService;


//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
@Controller //
@RequestMapping("/board")
public class BoardController {
	
	@Autowired 
	private BoardRepository BoardRepository;
	@Autowired 
	private CarInfoRepository carInfoRepository;
	@Autowired 
	private BoardService boardService;

	
//	@GetMapping("/list")
//	public String list(Model model) {
//		//model에 원하는 값을 넘겨주면됨
//		List<Board> boards = BoardRepository.findAll();
//		model.addAttribute("boards",boards);
//		return "/board/list";
//	}
	////////////////////////////////////////////////////////////////
	@GetMapping(value = {"list", "list/{optCp}"})
	public ResultBean<PageBean<Board>> list(@PathVariable //int currentPage){
															Optional<Integer> optCp){
		ResultBean<PageBean<Board>> rb = new ResultBean<>();
		try {
			int currentPage;
			if(optCp.isPresent()) {
				currentPage = optCp.get();
			}else {
				currentPage = 1;
			}
			PageBean<Board> pb = boardService.boardList(currentPage);
			rb.setStatus(1);
			rb.setT(pb);
		} catch (FindException e) {
			e.printStackTrace();
			rb.setStatus(0);
			rb.setMsg(e.getMessage());
		}
		return rb;
	}
	
	@GetMapping("/findpassengerlist")
	public String findPassengerList(Model model) {
		// model에 원하는 값을 넘겨주면됨
		// 리스트에서는 carInfo가 필요하지 않을 것 같다
		List<Board> boards = BoardRepository.findByboardStatus(1);
		model.addAttribute("boards",boards);
		return "/board/findpassengerlist";
	}
	
	
//hello
	@Transactional
	@GetMapping("/deletecarinfonboard")
    public String deleteCarInfoNBoard(Model model, Long boardNo){
		carInfoRepository.deleteById(boardNo);
		BoardRepository.deleteById(boardNo);
		return "redirect:/board/findpassengerlist";
    }
	
	@GetMapping("/findcarlist")
	public String findCarList(Model model) {
		//model에 원하는 값을 넘겨주면됨
		List<Board> boards = BoardRepository.findByboardStatus(2);
		model.addAttribute("boards",boards);
		return "/board/findcarlist";
	}
	
	@GetMapping("/delete")
    public String delete(Model model, Long boardNo){
		BoardRepository.deleteById(boardNo);
		return "redirect:/board/findcarlist";
    }

	
	///////////////////////////////////////////////////////////////////////////////
	

	
	// 글 쓰기 및 글 수정
	@GetMapping("/form")
	public String form(Model model, 
			@RequestParam(required = false) Long boardNo) {
		//boardNo가 null인지 판단하기 위헤 Integer 사용, int&Long은 null체크 못함
		//@RequestParam : 필수인지 아닌지
		if(boardNo==null) { //null일 경우 새 보드를 생성해서 타임리프에 넘겨줌
			
			model.addAttribute("board",new Board());
			//model.add
		}else {//id가 값이 있을 경우 보드레파지에서 조회해서 넘겨줌
			Board board = BoardRepository.findByboardNo(boardNo);//.orElse(null);
			model.addAttribute("board", board);
		}
		
		return "/board/form";
	}
	
	
	@PostMapping("/form")
	public String formSubmit(@Validated Board board, BindingResult bindingResult) {
	//유효성 검사 어노테이션
		//System.out.println("시간시간시간"+board.getBoardStarttime());
		System.out.println("@@보드보드"+board.getBoardStarttime());
		BoardRepository.save(board);
		//save에서 @id 값이 있는 경우엔 update가 실행되고, 없는경우엔 새로 생성됨
		return "redirect:/board/list";
		//redirect로 페이지 이동함
	}

//	@Transactional
//	@GetMapping("/delete")
//    public String boardCDelete(Model model, Integer boardNo){
//		BoardRepository.deleteByboardNo(Long.valueOf(boardNo));
//		return "redirect:/board/list";
//    }

}
