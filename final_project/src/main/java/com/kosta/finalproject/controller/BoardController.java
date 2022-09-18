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
import com.kosta.finalproject.model.CarInfo;
import com.kosta.finalproject.model.Findpassengerform;
import com.kosta.finalproject.model.Passenger;
import com.kosta.finalproject.repository.BoardRepository;
import com.kosta.finalproject.repository.CarInfoRepository;
import com.kosta.finalproject.repository.PassengerRepository;
import com.kosta.finalproject.service.BoardService;

//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함

@Controller
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

	/**
	 * @author hyeonho
	 * @param model
	 * @param pageable
	 * @param searchKeyword
	 * @return
	 */
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

	/**
	 * @author hyeonho
	 * @param model
	 * @param pageable
	 * @param searchKeyword
	 * @return
	 */
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

	/**
	 * @author hyeonho
	 * @param model
	 * @param pageable
	 * @param searchKeyword
	 * @return
	 */
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

	/**
	 * @author heeeun
	 * @param model
	 * @param boardNo
	 * @return
	 */
	@GetMapping("/findpassengerform")
	public String driverBoard(Model model, @RequestParam(required = false) Long boardNo, HttpSession session) {

		MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");
		Long fullSeats = fullSeat(boardNo);
		// boardNo가 null인 경우->새 글을 쓰게됨
		if (boardNo == null) {
			Board board = new Board();
			CarInfo carInfo = new CarInfo();
			// 새 글을 쓸 때 글쓴이 이름을 자동을 받아옴, 수정 불가능
			board.setMemberId(memberDto.getMemberId());
			board.setBoardStatus(1);
			
			Findpassengerform findPassengerform = new Findpassengerform();
			findPassengerform.setBoard(board);
			findPassengerform.setCarInfo(carInfo);
			model.addAttribute(findPassengerform);
			model.addAttribute("fullseat",fullSeats);
		} else {
			// bodrdNo가 있는 경우 보드레파지토리에서 조회함
			Board board = BoardRepository.findByboardNo(boardNo);
			CarInfo carInfo = carInfoRepository.findByboardNo(boardNo);
			List<Passenger> passengers = PassengerRepository.findByboardNo(boardNo);

			Findpassengerform findPassengerform = new Findpassengerform();
			// 게시판 상태 1로 설정해줌
			findPassengerform.setBoard(board);
			findPassengerform.getBoard().setBoardStatus(1);
			
			findPassengerform.setCarInfo(carInfo);
			
			model.addAttribute(findPassengerform);
			model.addAttribute("passenger", passengers);
			model.addAttribute("fullseat",fullSeats);
		}
		return "/board/findpassengerform";

//		if(boardNo == null) {
//			model.addAttribute("board",new Board());
//			model.addAttribute("carInfo", new CarInfo());
//		} else {
//			Board board = BoardRepository.findByboardNo(boardNo);
//			CarInfo carInfo =carInfoRepository.findByboardNo(boardNo);
//			model.addAttribute("board", board);
//			model.addAttribute("carInfo", carInfo);
//		}
//		return "/board/findpassengerlist";
	}

	/**
	 * @author heeeun
	 * @param board
	 * @param carInfo
	 * @return
	 */
	
//	public String insertDriverBoard(Board board, @RequestParam(required = false) CarInfo carInfo){
////		logger.error("board = " + board);
////		logger.error("carInfo= " + carInfo);
//		System.out.println("CarInfo : " + carInfo.getCarType());
//		Long insertedBoardNo = BoardRepository.save(board).getBoardNo();
//		carInfo.setBoardNo(board.getBoardNo());
//		carInfoRepository.save(carInfo);
//		return "redirect:/board/findpassengerlist";
//	}
	@PostMapping("/findpassengerform")
	public String insertDriverBoard(Findpassengerform findpassengerform) {
		//Findpassengerform findPassengerform = new Findpassengerform();
		// 게시판 상태 1로 설정해줌
		findpassengerform.getBoard().setBoardStatus(1);
		
		BoardRepository.save(findpassengerform.getBoard());
		CarInfo carInfo = findpassengerform.getCarInfo();
		carInfo.setBoardNo(findpassengerform.getBoard().getBoardNo());
		carInfoRepository.save(carInfo);
		
		return "redirect:/board/findpassengerlist";
	}

	/**
	 * @author heeeun
	 * @param model
	 * @param boardNo
	 * @return
	 */
	@Transactional
	 @GetMapping("/findpassengerdelete")
	    public String deleteCarInfoNBoard(Model model, Long boardNo){
	      carInfoRepository.deleteByboardNo(boardNo);
	      BoardRepository.deleteByboardNo(boardNo);
	      //return "redirect:/board/findcarlist";
	      return "redirect:/board/findpassengerlist";
	    }

	/**
	 * @author koeun
	 * @param boardNo
	 * @param loginId
	 * @param session
	 * @return
	 */
	@GetMapping("/findpassengeradd")
	public String passengerboardadd(Long boardNo, @RequestParam(required = false) String loginId,
			HttpSession session) {
		// 로그인한 사용자의 정보 가져오기
		MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");

		if (PassengerRepository.findByBoardNoAndPassengerId(boardNo, loginId) != null) {
			System.out.println("@@@@if로들어옴");
			// 운전자 신청을 이미 한 사람은 삭제됨
			return "redirect:/board/findpassengerdeletepassenger?boardNo=" + boardNo + "&passengerId=" + loginId;
		} else {
			// 운전자신청을 처음 누른 사람은 등록됨
			// 새 passenger 객체 만들어서 테이블에 추가함
			Passenger passenger = new Passenger();
			passenger.setBoardNo(boardNo);
			passenger.setPassengerId(memberDto.getMemberId());
			PassengerRepository.save(passenger);

			return "redirect:/board/findpassengerform?boardNo=" + boardNo;

		}
	}

	// 글쓴이 입장에서 승인 또는 취소
	/**
	 * @author koeun
	 * @param boardNo
	 * @param passengerId
	 * @return
	 */
	@GetMapping("/findpassengerconfirm")
	public String passengerboardconfirm(Long boardNo, String passengerId) {

		Passenger passenger = PassengerRepository.findByBoardNoAndPassengerId(boardNo, passengerId);
		passenger.setPassengerCheck("승인");
		PassengerRepository.save(passenger);

		return "redirect:/board/findpassengerform?boardNo=" + boardNo;

	}

	/**
	 * @author koeun
	 * @param boardNo
	 * @param passengerId
	 * @return
	 */
	@Transactional
	@GetMapping("/findpassengerdeletepassenger")
	public String passengerboarddeletePassenger(Long boardNo, @RequestParam(required = false) String passengerId) {
		if (passengerId != null) {
			Passenger passenger = PassengerRepository.findByBoardNoAndPassengerId(boardNo, passengerId);
			PassengerRepository.deleteBypassengerboardNo(passenger.getPassengerboardNo());
		}
		return "redirect:/board/findpassengerform?boardNo=" + boardNo;
	}

	// 글 쓰기 및 글 수정
	/**
	 * @author koeun
	 * @param model
	 * @param boardNo
	 * @param session
	 * @return
	 */
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

	/**
	 * @author koeun
	 * @param board
	 * @param passenger
	 * @return
	 */
	@PostMapping("/findcarform")
	public String formSubmit(Board board, @RequestParam(required = false) Passenger passenger) {
		board.setBoardStatus(2);
		BoardRepository.save(board);
		return "redirect:/board/findcarlist";
		// redirect로 페이지 이동함
	}

	/**
	 * @author koeun
	 * @param boardNo
	 * @param loginId
	 * @param session
	 * @return
	 */
	@GetMapping("/findcaraddpassenger")
	public String addPassenger(Long boardNo, @RequestParam(required = false) String loginId, HttpSession session) {
		// 로그인한 사용자의 정보 가져오기
		MemberDTO memberDto = (MemberDTO) session.getAttribute("loginInfo");

		if (PassengerRepository.findByBoardNoAndPassengerId(boardNo, loginId) != null) {
			// 운전자 신청을 이미 한 사람은 삭제됨
			return "redirect:/board/findcardeletepassenger?boardNo=" + boardNo + "&passengerId=" + loginId;
		} else {
			// 운전자신청을 처음 누른 사람은 등록됨
			// 새 passenger 객체 만들어서 테이블에 추가함
			Passenger passenger = new Passenger();
			passenger.setBoardNo(boardNo);
			passenger.setPassengerId(memberDto.getMemberId());
			PassengerRepository.save(passenger);

			return "redirect:/board/findcarform?boardNo=" + boardNo;

		}
	}

	// 글쓴이 입장에서 승인 또는 취소
	/**
	 * @author koeun
	 * @param boardNo
	 * @param passengerId
	 * @return
	 */
	@GetMapping("/findcarconfirmpassenger")
	public String confirmPassenger(Long boardNo, String passengerId) {

		Passenger passenger = PassengerRepository.findByBoardNoAndPassengerId(boardNo, passengerId);
		passenger.setPassengerCheck("승인");
		PassengerRepository.save(passenger);

		return "redirect:/board/findcarform?boardNo=" + boardNo;

	}

	/**
	 * @author koeun
	 * @param boardNo
	 * @param passengerId
	 * @return
	 */
	@Transactional
	@GetMapping("/findcardeletepassenger")
	public String deletePassenger(Long boardNo, @RequestParam(required = false) String passengerId) {
		if (passengerId != null) {
			Passenger passenger = PassengerRepository.findByBoardNoAndPassengerId(boardNo, passengerId);
			PassengerRepository.deleteBypassengerboardNo(passenger.getPassengerboardNo());
		}
		return "redirect:/board/findcarform?boardNo=" + boardNo;
	}

	/**
	 * @author koeun
	 * @param model
	 * @param boardNo
	 * @return
	 */
	@Transactional
	@GetMapping("/findcardelete")
	public String boardDelete(Model model, Long boardNo) {
		BoardRepository.deleteByboardNo(boardNo);
		return "redirect:/board/findcarlist";
	}
	
	
//	남은 좌석 수를 계산하는 메소드
//	@GetMapping("/f")
	public Long fullSeat(Long boardNo) {
		String s = "승인";
		List<Passenger> passengers = PassengerRepository.findByBoardNoAndPassengerCheck(boardNo, s);
		
		System.out.println((long) passengers.size());
		System.out.println((long) passengers.size());
		System.out.println((long) passengers.size());
		System.out.println((long) passengers.size());
		System.out.println((long) passengers.size());
		System.out.println((long) passengers.size());
		return (long) passengers.size();
	}

}