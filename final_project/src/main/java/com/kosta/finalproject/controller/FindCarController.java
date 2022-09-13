//package com.kosta.finalproject.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.kosta.finalproject.model.FindCarBoard;
//import com.kosta.finalproject.repository.BoardRepository;
//
//
////뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
////바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
//@Controller
//@RequestMapping("/findcarboard")
//public class FindCarController {
//	
//	@Autowired 
//	private BoardRepository FindCarBoardRepository;
//
//	
//	@GetMapping("/list")
//	public String list(Model model) {
//		//model에 원하는 값을 넘겨주면됨
//		List<FindCarBoard> boards = FindCarBoardRepository.findAll();
//		model.addAttribute("boards",boards);
//		return "/findcarboard/list";
//	}
//	
//	// 글 쓰기 및 글 수정
//	@GetMapping("/form")
//	public String form(Model model, @RequestParam(required = false) Integer boardnum) {
//		//@RequestParam : 필수인지 아닌지
//		if(boardnum==null) { //null일 경우 새 보드를 생성해서 타임리프에 넘겨줌
//			model.addAttribute("board",new FindCarBoard());
//			//model.add
//		}else {//id가 값이 있을 경우 보드레파지에서 조회해서 넘겨줌
//			FindCarBoard board = FindCarBoardRepository.findByboardnum(boardnum);//.orElse(null);
//			model.addAttribute("board", board);
//		}
//		
//		return "/findcarboard/form";
//	}
//	
//	
//	@PostMapping("/form")
//	public String formSubmit(@Validated FindCarBoard board, BindingResult bindingResult) {
//	//유효성 검사 어노테이션
//		FindCarBoardRepository.save(board);
//		//save에서 @id 값이 있는 경우엔 update가 실행되고, 없는경우엔 새로 생성됨
//		return "redirect:/findcarboard/list";
//		//redirect로 페이지 이동함
//	}
//	
//	@Transactional
//	@GetMapping("/delete")
//    public String boardDelete(Model model, Integer boardnum){
//		FindCarBoardRepository.deleteByboardnum(Long.valueOf(boardnum));
//		return "redirect:/findcarboard/list";
//    }
//
//}
