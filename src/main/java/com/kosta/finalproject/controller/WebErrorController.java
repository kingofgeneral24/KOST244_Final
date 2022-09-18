package com.kosta.finalproject.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/*에러페이지 이동처리*/

/*
 * 스프링부트에서 제공하는 ErrorController의 인터페이스를 구현한다.
 * 에러코드를 받아 해당 코드에 맞는 에러페이지로 이동시킨다.
 * 
 * */
@Slf4j
@Controller
public class WebErrorController implements ErrorController {
	
    public String getErrorPath() {
        return null;
    }

    @SuppressWarnings("unused")
	@GetMapping("/error")
    public String handleError(HttpServletRequest request) {
    	
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        log.info("status : "+status.toString());

        if(status != null){
            int statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/errors/404";
            } else {
                return "/errors/500";
            }
        }

        return "/errors/error";
    }
}
