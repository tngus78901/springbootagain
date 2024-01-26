package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.CustomPageException;
import com.tenco.bank.handler.exception.CustomRestfulException;

@Controller
@RequestMapping("/test") // 대문
public class TestController {
	
	// 주소설계
	// http://localhost:80/test/main
	@GetMapping("/main")
	public String mainPage() {
		
		// 인증 검사 (항상 먼저하기)
		// 유효성 검사
		// 뷰 리졸브 --> 해당하는 파일 찾아 (필요하면 data도 같이 보낸다)
		// return "/WEB-INF/view/layout/main.jsp
		// prefix: /WEB-INF
		// layout/main.jsp
		// suffix: .jsp
		// 예외발생
		throw new CustomRestfulException("페이지가 없네요", HttpStatus.NOT_FOUND);
		//return "layout/main";
		
	}
	

}
