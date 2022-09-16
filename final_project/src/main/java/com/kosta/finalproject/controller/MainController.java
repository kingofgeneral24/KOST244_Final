package com.kosta.finalproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.finalproject.dto.MemberDTO;

@Controller
public class MainController {
	// URL을 /로 접근하거나 /home/main으로 접근할 수 있다.
	@GetMapping ({"/","/home/main"})
	public String home(HttpSession session) {
		
		//로그인 세션에서 값을꺼내 컨트롤러에서 사용하는방법
		//로그인 세션객체를 회원 Dto로 맵핑함 (값을 꺼내기위해서)
		//MemberDTO memberDto  = (MemberDTO) session.getAttribute("loginInfo");
		//memberDto.getMemberId() or memberDto.getMemberName();
		//BoardDto.setUserId(memberDto.getMemberId()); //게시판 작성자 dto변수에 set하면됨.
		//게시판인서트하면 작성자가 > 로그인한사람이 됨
		
		
		return "/home/main"; 
	}

}
