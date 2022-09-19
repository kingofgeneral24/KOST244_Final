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
	public String memberUpdate(MemberDTO memberDTO) {

		log.info("memberDTO : "+memberDTO.toString());
		
		
//		html에서 받은 DTO를 업데이트
//		수정하는 로직 작성부분
//		
//		비밀번호 변경 로직 설명
//       
//      기존비밀번호가 맞는지 dto.memberPassword에 값을 넣고 ajax로  체크 해야한다.
//       
//      세션이 있는 로그인 한 계정의 member_no값으로 DB에 저장된 회원정보를 가져온다. where member_no = 로그인한 member_no
//      
//       ajax로 보낸 dto.getMemberPassword 랑 db의 memberPassword랑 비교
//     
//      일치한다면 새로운 비밀번호입력한것을 다시 dto.memberPassword에 담아 서버로 보내고 변경처리를 한다.
//		
		
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
	public String save(MemberDTO memberDTO) {
		
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
	
	//회원 탈퇴  /member/delete/3
	@GetMapping("/delete/{memberId}")
	public String delete(@PathVariable String memberId) {
		
		MemberDTO memberDTO = memberService.findByMemberId(memberId);
		
		Long memberNo =memberDTO.getMemberNo();
		
		memberService.delete(memberNo);
		
		return "redirect:/login/logout";

	//	return"memberPages.list";//X
	//ajax안쓰는 거
	}
	

	/**
	  * /member/3: 조회(get) R, 저장(post) C, 수정(put) U, 삭제(delete)  D
	  */
	
	/* 유튜브 회원탈퇴
	@DeleteMapping("/deleteMember")
	public ResponseEntity deleteAjax(@PathVariable Long memberNo) {
		memberService.delete(memberNo);
		return new ResponseEntity<>(HttpStatus.OK);  ajax  쓰는 거/호출한 부분에 리턴으로 200 응답을 줌.
		
	}
	*/
	

	/*비밀번호 변경페이지에서 현재 비밀번호 검증*/
   @PostMapping("/pwChk")
   @ResponseBody
   public String login (MemberDTO memberDTO, HttpSession session) {
	   
	   //로그인 세션정보를 dto에 맵핑한다.
	   MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");
          
      //로그인 세션에 있는 현재 비밀번호
      String nowPw = loginInfo.getMemberPassword();
      //html 비밀번호 변경페이지에서 입력한 현재 비밀번호
      String inputPw = memberDTO.getMemberPassword();
	      
      return memberService.pwChk(inputPw, nowPw);
   }  

	/*비밀번호 변경하기*/
   @PostMapping("/updatePassword")
   public String updatePassword (MemberDTO memberDTO, HttpSession session) {
	   
	   //로그인 세션정보를 dto에 맵핑한다.
	   MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");
	   
	   memberDTO.setMemberNo(loginInfo.getMemberNo());
	   
	   log.info("memberDTOupdatePassword  >>>>>>>>>>>>>>> "+memberDTO.toString());
      
	   Long result = memberService.updatePassword(memberDTO);
      
	   if(result >0 ) {
		   return "redirect:/member/myPage";    	  
	   }else {
		   return "redirect:/member/myPage";
      }
	   
   }

	/*회원정보 변경하기*/
   @PostMapping("/updateMember")
   public String updateMember(MemberDTO memberDTO) {

	   log.info("memberDTO updateMember  >>>>>>>>>>>>>>> "+memberDTO.toString());
	   
	   Long result = memberService.updateMember(memberDTO);
	   
	   if(result >0 ) {
		   return "redirect:/member/myPage";    	  
	   }else {
		   return "redirect:/member/myPage";
      }

   }
}
