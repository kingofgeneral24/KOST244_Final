package com.kosta.finalproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.finalproject.controller.MemberController;
import com.kosta.finalproject.dto.MemberDTO;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : kr.co
* @fileName       : LoginIntercepter.java
* @author         : HYE
* @date           : 2022.08.28
* @description    : 로그인 인터셉터 관련 설정파일, 추후작업 예정
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.08.28        Hye      			최초 생성
*/

//1.1 인터셉터란?
//컨트롤러(Controller)의 '핸들러(Handler)'를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할수 있는 일종의 필터
//1.2 왜 사용하는가?
//개발자는 특정 Controller의 핸들러가 실행되기 전이나 후에 추가적인 작업을 원할때 Interceptor를 사용한다.
// 1) preHandle()란 ? 컨트롤러가 호출되기 전에 실행됨
// 2) postHandle()란? 핸들러가 실행은 완료 되었지만 아직 View가 생성되기 이전에 호출된다.
// 3) afterCompletion()란? 모든 View에서 최종 결과를 생성하는 일을 포함한 모든 작업이 완료된 후에 실행된다.

@Slf4j
public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	//컨트롤러 리퀘스트맵핑 주소를 호출하기전에 로그인 세션을 체크한다.
    	 HttpSession session = request.getSession();
    	MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");

    	//로그인 세션정보를 인터셉터에서 체크한다. 없으면 로그인 페이지로 이동, 접근 통제는 WebConfig.java에서함
        if(ObjectUtils.isEmpty(loginInfo)){
    		log.info("로그인 정보가 없습니다.");
    		//로그인정보가 없으면 로그인 페이지로 강제이동
        	response.sendRedirect("/login/loginForm"); //이건 컨트롤러 리퀘스트 맵핑주소
        }
    	
    	return true;
        

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	log.info("view로 보내기전 작업은? : {}", response.getStatus());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    
}
