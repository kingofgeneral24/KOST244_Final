package com.kosta.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.model.Passenger;
import com.kosta.finalproject.repository.BoardRepository;
import com.kosta.finalproject.repository.CarInfoRepository;
import com.kosta.finalproject.repository.PassengerRepository;
import com.kosta.finalproject.service.BoardService;

//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
@Controller //
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository BoardRepository;

	@Autowired
	private PassengerRepository PassengerRepository;

	@Autowired
	private CarInfoRepository carInfoRepository;

	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public String boardList(Model model,
			@PageableDefault(page = 0, size = 10, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		Page<Board> list = null;
		if (searchKeyword == null) {
			list = boardService.boardList(pageable);
		} else {
			list = boardService.boardSearchList(searchKeyword, pageable);
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/board/list";
	}

	@GetMapping("/findpassengerlist")
	public String findPassengerList(Model model,
			@PageableDefault(page = 0, size = 10, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		Page<Board> list = null;
		if (searchKeyword == null || searchKeyword == "") {
			list = boardService.boardStatusList(1, pageable);
		} else {
			list = boardService.boardSearchList(searchKeyword, pageable);
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/board/findpassengerlist";
	}

//	@Transactional
//	@GetMapping("/deletecarinfonboard")
//    public String deleteCarInfoNBoard(Model model, Long boardNo){
//		carInfoRepository.deleteById(boardNo);
//		BoardRepository.deleteById(boardNo);
//		return "redirect:/board/findpassengerlist";
//    }

//	@GetMapping("/findcarlist")
//	public String list(Model model) {
//		// model에 원하는 값을 넘겨주면됨
//		List<Board> boards = BoardRepository.findAll();
//		// List<Board> boards = BoardRepository.findStatus2();
//		model.addAttribute("boards", boards);
//		return "/board/findcarlist";
//	}
	@GetMapping("/findcarlist")
	public String findCarList(Model model,
			@PageableDefault(page = 0, size = 10, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		Page<Board> list = null;
		if (searchKeyword == null || searchKeyword == "") {
			list = boardService.boardStatusList(2, pageable);
		} else {
			list = boardService.boardSearchList(searchKeyword, pageable);
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/board/findcarlist";
	}

	// 글 쓰기 및 글 수정
	@GetMapping("/findcarform")
	public String form(Model model, @RequestParam(required = false) Long boardNo, HttpSession session) {

		if (boardNo == null) { // null일 경우 새 보드를 생성해서 타임리프에 넘겨줌
			// 새 글 생성
			Board board = new Board();
			// Passenger passenger = new Passenger();
			// 로그인 세션 유지해서 글 쓸때 ID 자동으로 입력되게함
			MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");
			board.setMemberId(memberDto.getMemberId());
			board.setBoardStatus(2);

			// model.addAttribute("Passenger",passenger);
			model.addAttribute("board", board);
		} else {// id가 값이 있을 경우 보드레파지에서 조회해서 넘겨줌
			Board board = BoardRepository.findByboardNo(boardNo);// .orElse(null);
			List<Passenger> passengers = PassengerRepository.findByboardNo(boardNo);
			model.addAttribute("board", board);
			model.addAttribute("passenger", passengers);
		}

		return "/board/findcarform";
	}

	@PostMapping("/findcarform")
	public String formSubmit(Board board, @RequestParam(required = false) Passenger passenger) {
		board.setBoardStatus(2);
		BoardRepository.save(board);
		return "redirect:/board/findcarlist";
		// redirect로 페이지 이동함
	}

	@GetMapping("/findcaraddpassenger")
	public String addPassenger(Long boardNo,@RequestParam(required = false) String loginId, HttpSession session) {
		// 로그인한 사용자의 정보 가져오기
		MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");

		if (PassengerRepository.findByBoardNoAndPassengerId(boardNo, loginId) != null) {
			System.out.println("@@@@@@test1");
			return "redirect:/board/findcardeletepassenger?boardNo="+boardNo+"&passengerId="+loginId;
			
					///findcardeletepassenger(boardNo=${board.boardNo},passengerId=${passenger.passengerId
		} else {
			// 새 passenger 객체 만들어서 테이블에 추가함
			Passenger passenger = new Passenger();
			passenger.setBoardNo(boardNo);
			passenger.setPassengerId(memberDto.getMemberId());
			PassengerRepository.save(passenger);
		
		return "redirect:/board/findcarform?boardNo="+boardNo;
		}
	}

	@Transactional
	@GetMapping("/findcardeletepassenger")
	public String deletePassenger(Long boardNo, @RequestParam(required = false) String passengerId,
			HttpSession session) {
		if (passengerId != null) {
			Passenger passenger = PassengerRepository.findByBoardNoAndPassengerId(boardNo, passengerId);
			PassengerRepository.deleteBypassengerboardNo(passenger.getPassengerboardNo());
		}
		return "redirect:/board/findcarform?boardNo=" + boardNo;
	}

	@Transactional
	@GetMapping("/findcardelete")
	public String boardDelete(Model model, Integer boardNo) {
		BoardRepository.deleteByboardNo(Long.valueOf(boardNo));
		return "redirect:/board/findcarlist";
	}

}
