package com.kosta.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.finalproject.dto.PagingDTO;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;
import com.kosta.finalproject.repository.CarInfoRepository;
import com.kosta.finalproject.service.BoardService;


//뷰와 모델의 다리역할, 뷰로부터 사용자의 인터랙션을 받아 모델에 전달하고, 
//바뀐 모델 데이터를 뷰에 다시 전달하여 업데이트함
@Controller
@RequestMapping("/board")
//@CrossOrigin(origins="*")
//@RestController
//@RequestMapping("/board")
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
//	@GetMapping(value = {"list", "list/{optCp}"})
//	public ResultBean<PageBean<Board>> list(@PathVariable Optional<Integer> optCp){
//		ResultBean<PageBean<Board>> rb = new ResultBean<>();
//		try {
//			int currentPage;
//			if(optCp.isPresent()) {
//				currentPage = optCp.get();
//			}else {
//				currentPage = 1;
//			}
//			PageBean<Board> pb = boardService.boardList(currentPage);
//			rb.setStatus(1);
//			rb.setT(pb);
//		} catch (FindException e) {
//			e.printStackTrace();
//			rb.setStatus(0);
//			rb.setMsg(e.getMessage());
//		}
//		return rb;
//	}
	
	// -------------페이징--------------
	@GetMapping("/")
	public String index(Model model, @PageableDefault(size=5, sort="id", direction=Sort.Direction.DESC)Pageable pageable) {
		Page<Board> boards = boardService.findAll(pageable);
		int startPage = Math.max(1,  boards.getPageable().getPageNumber() - 4);
		int endPage = Math.max(boards.getTotalPages(), boards.getPageable().getPageNumber()+4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boards", boards);
		return "index";
	}
	// --------------------------------
	
	// ---------검색---------
	@GetMapping("/")
    public String index(Model model,
                        @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") String search) {
        Page<Board> boards = boardService.findByTitleContainingOrContentContaining(search, search, pageable);
        return "index";
    }
	
	
//	@GetMapping("/board/findcarlist")
//	public String boardList(Model model, @PageableDefault(page=0, size=10, sort="id", direction=Sort.Direction.DESC) Pageable pageable, String searchKeyword) {
//		Page<Board> list = null;
//		if(searchKeyword == null) {
//			list = boardService.boardList(pageable);
//		}else {
//			list = boardService.boardSearchList(searchKeyword, pageable);
//		}
//	}

	
//	@CrossOrigin(origins = "*", allowedHeaders="*")
//	@GetMapping("/board/page")
//	public Page<PagingDTO> paging(@PageableDefault(size=5, sort="createdTime") Pageable pageRequest){
//		Page<Board> postList = BoardRepository.findAll(pageRequest);
//		Page<PagingDTO> pagingList = postList.map(
//				post -> new PagingDTO(
//						post.getMemberId(), post.getBoardTitle()));
//		return pagingList;
//	}
	
//	@CrossOrigin(origins="*", allowedHeaders="*")
//	@GetMapping("/board/page/search")
//	public Page<PagingDTO> searchPaging(
//			@RequestParam String title,
//			@RequestParam String content,
//			@PageableDefault(size=5, sort="createdTime") Pageable pageRequest){
//		Page<Board> postList = BoardRepository.findAllSearch(title, content, pageRequest);
//		Page<PagingDTO> pagingList = postList.map(
//				post -> new PagingDTO(
//						post.getMemberId(), post.getBoardTitle())
//				);
//		return pagingList;
//				
//	}
	
//	@GetMapping("/board/search")
//	public String search(@RequestParam(value="keyword") String keyword, Model model) {
//		model.addAttribute("postList", boardService.searchPosts(keyword));
//		return "board/findcarlist.html";
//	}
	
	
//	@GetMapping("/getSearchList")
//	@ResponseBody
//	private List<Board> getSearchList(@RequestParam("type") String type,
//				@RequestParam("keyword") String keyword, Model model) throws Exception{
//			Board board = new Board();
//			board.setType(type);
//			board.setKeyword(keyword);
//			return boardService.getSearchList(board);
//	}
	// -----------------------------------------------------
	
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Board> paging = this.boardService.getList(page);
		model.addAttribute("paging", paging);
		return "list";
	}

	@GetMapping("/findpassengerlist")
	public String findPassengerList(Model model) {
		// model에 원하는 값을 넘겨주면됨
		// 리스트에서는 carInfo가 필요하지 않을 것 같다
		List<Board> boards = BoardRepository.findByboardStatus(1);
		model.addAttribute("boards",boards);
		
		
		////////////////////////////
//	public String findPassengerList(Model model, @PageableDefault(page=0, size=10, direction=Sort.Direction.DESC) Pageable pageable) {
//		Page<Board> list = boardService.boardList(pageable);
//		int nowPage = list.getPageable().getPageNumber() + 1;
//		int startPage = Math.max(nowPage - 4,  1);
//		int endPage = Math.min(nowPage+5,  list.getTotalPages());
//		
//		model.addAttribute("list",list);
//		model.addAttribute("nowPage",nowPage);
//		model.addAttribute("startPage",startPage);
//		model.addAttribute("endPage",endPage);
		////////////////////////////
		return "/board/findpassengerlist";
	}
	
	@Transactional
	@GetMapping("/deletecarinfonboard")
    public String deleteCarInfoNBoard(Model model, Long boardNo){
		carInfoRepository.deleteById(boardNo);
		BoardRepository.deleteById(boardNo);
		return "redirect:/board/findpassengerlist";
    }
	
//	@GetMapping("/findcarlist")
//	public String findCarList(Model model) {
//		//model에 원하는 값을 넘겨주면됨
//		List<Board> boards = BoardRepository.findByboardStatus(2);
//		model.addAttribute("boards",boards);
//		return "/board/findcarlist";
//	}
	
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
