package com.kosta.finalproject.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component

public class AdminBoardValidator {
	  @Override
	    public boolean supports(Class<?> clazz) {
	        return AdminBoard.class.equals(clazz);
	    }

	    @Override
	    public void validate(Object obj, Errors errors) {
	        AdminBoard b = (Board) obj;
	        if(StringUtils.isEmpty(b.getContent())) {
	            errors.rejectValue("content", "key", "내용을 입력하세요");
	        }
	    }

}
