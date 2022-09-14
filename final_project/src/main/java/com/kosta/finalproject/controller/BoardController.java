package com.kosta.finalproject.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.model.CarInfo;
import com.kosta.finalproject.repository.BoardRepository;
import com.kosta.finalproject.repository.CarInfoRepository;


//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
@Controller //
@RequestMapping("/board")
public class BoardController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired 
	private BoardRepository BoardRepository;
	@Autowired
	private CarInfoRepository carInfoRepository;
	
	@GetMapping("/list")
	public String list(Model model) {
		//model에 원하는 값을 넘겨주면됨
		List<Board> boards = BoardRepository.findAll();
		model.addAttribute("boards",boards);
		return "/board/list";
	}
	
	// 글 쓰기 및 글 수정
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long boardNo) {
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
	
	@GetMapping("/insertDriverBoard")
	public String driverBoard(Model model, Long boardNo) {
		if(boardNo == null) {
			model.addAttribute("driverBoard",new Board());
			model.addAttribute("carInfo", new CarInfo());
		} else {
			Optional<Board> driverBoard = BoardRepository.findById(boardNo);
			Optional<CarInfo> carInfo =carInfoRepository.findById(boardNo);
			model.addAttribute("driverBoard", driverBoard);
			model.addAttribute("carInfo", carInfo);
		}
		return "aaa";
	}
	
	@PostMapping("/insertDriverBoard")
	public String insertDriverBoard(Board board
			,
			CarInfo carInfo
			)
	{
		logger.error("board = " + board);
		logger.error("carInfo= " + carInfo);
		Long insertedBoardNo = BoardRepository.save(board).getBoardNo();
		carInfo.setBoardNo(insertedBoardNo);
		carInfoRepository.save(carInfo);
		return "aaa";
	}
	
	
//	@Transactional
//	@GetMapping("/delete")
//    public String boardDelete(Model model, Integer boardNo){
//		//carInfoRepository.deleteById(boardNo);
//		BoardRepository.deleteByboardNo(Long.valueOf(boardNo));
//		return "redirect:/board/list";
//    }
	
	 @GetMapping("/delete")
	    public String boardDelete(Model model, Long boardNo){
	      BoardRepository.deleteById(boardNo);
	      //return "redirect:/board/findcarlist";
	      return "aaa";
	    }
	   

	   @Transactional
	   @GetMapping("/deletecarinfonboard")
	    public String deleteCarInfoNBoard(Model model, Long boardNo){
	      carInfoRepository.deleteById(boardNo);
	      BoardRepository.deleteById(boardNo);
	      //return "redirect:/board/findcarlist";
	      return "aaa";
	    }

}
