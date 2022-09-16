package com.kosta.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : com.kosta.finalproject.controller
* @fileName        : MemberController.java
* @author        : Hye
* @date            : 2022.09.09
* @description   : 회원 관리 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.09.09        Hye       최초 생성
*/

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")

public class MemberController {
	private final MemberService memberService;

	/**
	* @methodName    : joinForm
	* @author        : Hye
	* @date        : 2022.09.09
	* @description   :  회원가입 페이지 이동
	* @return
	*/
	@GetMapping("/joinForm")
	public String joinForm() {
		return "/member/joinForm";
	}	
	
	/**
	* @methodName    : myPage
	* @author        : Hye
	* @date        : 2022.09.09
	* @description   : 마이페이지 상세보기 이동
	* @return	
	*/
	
	@GetMapping("/myPage")
	public String myPage(Model model, HttpSession session) {
		
		Long memberNo = null;
		//로그인 세션정보를 dto에 맵핑한다.
   	 	MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");

   	 	//로그인 세션정보가 있을때
        if(!ObjectUtils.isEmpty(loginInfo)){
        	//dto에서 회원번호를 가져와서 변수에 담는다.
        	memberNo = loginInfo.getMemberNo();
        	//회원번호로 회원정보를 데이터베이스에서 조회 후 html에서 사용하기위하여 모델 객체에 담는다.
        	model.addAttribute("memberInfo", memberService.findByMemberNo(memberNo));
        }
		
		return "/member/myPage";
		
	}	

	/*마이페이지 수정페이지 이동*/
	@GetMapping("/myPageUpdateForm")
	public String myPageUpdateForm(Model model, HttpSession session) {

		log.info("myPageUpdateForm 페이지이동 ");
		
		Long memberNo = null;
		//로그인 세션정보를 dto에 맵핑한다.
   	 	MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");

   	 	//로그인 세션정보가 있을때
        if(!ObjectUtils.isEmpty(loginInfo)){
        	//dto에서 회원번호를 가져와서 변수에 담는다.
        	memberNo = loginInfo.getMemberNo();
        	//회원번호로 회원정보를 데이터베이스에서 조회 후 html에서 사용하기위하여 모델 객체에 담는다.
        	model.addAttribute("memberInfo", memberService.findByMemberNo(memberNo));
        }
		return "/member/myPageUpdateForm";
		
	}	

	/*JPA 수정하기 html에서 수정하기버튼 눌렀을때 아래의 url로 호출함*/
	@PostMapping("/memberUpdate")
	public String memberUpdate(@ModelAttribute MemberDTO memberDTO) {

		log.info("memberDTO : "+memberDTO.toString());
		
		//1. 숙제 회원정보 수정하기.
		//2. html에서 받은 DTO를 업데이트하시오
		//수정하는 로직 작성부분
		
		
		return "redirect:/member/myPage";		//수정 후 마이페이지로 이동하기
		
	}	
	
	/*비밀번호 수정페이지 이동*/
	@GetMapping("/passwordUpdateForm")
	public String passwordUpdateForm(Model model, HttpSession session) {

		log.info("passwordUpdateForm 페이지이동 ");
		
		Long memberNo = null;
		//로그인 세션정보를 dto에 맵핑한다.
   	 	MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");

   	 	//로그인 세션정보가 있을때
        if(!ObjectUtils.isEmpty(loginInfo)){
        	//dto에서 회원번호를 가져와서 변수에 담는다.
        	memberNo = loginInfo.getMemberNo();
        	//회원번호로 회원정보를 데이터베이스에서 조회 후 html에서 사용하기위하여 모델 객체에 담는다.
        	model.addAttribute("memberInfo", memberService.findByMemberNo(memberNo));
        }
		return "/member/passwordUpdateForm";
		
	}	
	
	@PostMapping("/save")
	public String save(@ModelAttribute MemberDTO memberDTO) {
		
		log.info("memberDTO : "+memberDTO.toString());
		
		memberService.save(memberDTO);
		return "redirect:/login/loginForm";
		
	}
	
	@GetMapping("/")
	public String findAll(Model model) {

		List<MemberDTO> memberDTOList =memberService.findAll();
		model.addAttribute("memberList", memberDTOList);
		return "/member/list";
		
	}
	
	
	/**
	* @methodName    : findById
	* @author        : Hye
	* @date        : 2022.09.09
	* @description   :  아이디 중복검사
	* @param memberId
	* @param model
	* @return
	*/
	@GetMapping("/idChk/{memberId}")
	@ResponseBody
	public String findByMemberId(@PathVariable String memberId) {
		
		String result = "";
		
		MemberDTO memberDTO = memberService.findByMemberId(memberId);
		
		if(memberDTO != null) {
			result = "N";
		}else {
			result = "Y";
		}
		
		return result;
	}
	
	//ajax상세 조회
	@PostMapping("/ajax/{id}")
	public @ResponseBody MemberDTO findByIdAjax(@PathVariable String memberId) {
		MemberDTO memberDTO = memberService.findByMemberId(memberId);
		return memberDTO;
	}
	
	//get 요청 삭제 /member/delete/3
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id ) {
		memberService.delete(id);
		return "redirect:/member/loginForm";
	//	return"memberPages.list";//X
	}
	
	/**
	  * /member/3: 조회(get) R, 저장(post) C, 수정(put) U, 삭제(delete)  D
	  */
	
		//delete요청 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity deleteAjax(@PathVariable Long id) {
		memberService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK); //ajax 호출한 부분에 리턴으로 200 응답을 줌.
		
	}
	}
