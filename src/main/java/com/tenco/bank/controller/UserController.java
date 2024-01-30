package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDTO;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;

@Controller  
@RequestMapping("/user")
public class UserController {
	
	@Autowired // DI 처리
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession; 
	
	/*
	 * 회원 가입 페이지 요청
	 * @return signUp.jsp 파일 리턴
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		// prefix: /WEB_INF/view
		// suffix: .jsp
		return "user/signUp";
	}
	
	/**
	 * 회원 가입 요청
	 * @param dto
	 * @return 로그인 페이지(/sign-in)
	 */
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDTO dto) {
		
		// 1. 인증검사 x
		// 2. 유효성 검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요", 
				HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하세요", 
				HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력하세요", 
				HttpStatus.BAD_REQUEST);
		}
		System.out.println("1111111");
		userService.createUser(dto);
		return "redirect:/user/sign-in"; 

	}
	
	/*
	 * 로그인 페이지 요청
	 * @return 
	 */
	@GetMapping("/sign-in")
	public String signInPage() {
		// 유효성 검사 x
		return "user/signIn";
	}
	/*
	 * 로그인 요청 처리
	 * @param SignInFromDto
	 * @return 추후 account/list 페이지로 이동 예정(todo)
	 */
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		
		// 1.유효성 검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하시오",
					HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하시오",
					HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출 예정
		User user = userService.readUser(dto);
		httpSession.setAttribute(Define.PRINCIPAL, user);
		// 로그인 완료 --> 결정(account/list)
		// todo 수정 예정 (현재 접근 경로 없음)
		return "redirect:/user/sign-in";
		
	}
	
		//로그아웃 기능 만들기
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
}


