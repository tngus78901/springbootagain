package com.tenco.bank.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bank.dto.KakaoProfile;
import com.tenco.bank.dto.OAuthToken;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDTO;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 비동기적으로 돌아가 성능상에 더 이점이 있다. sysout은 마이너스 
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
		
		System.out.println("dto : " + dto.toString());
		System.out.println(dto.getCustomFile().getOriginalFilename());
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
		
		// 파일 업로드
		MultipartFile file = dto.getCustomFile();
		System.out.println("file" + file.getOriginalFilename());
		if(file.isEmpty() == false) {
			// 사용자가 이미지를 업로드했다면 기능 구현
			// 파일 사이즈 체크
			// 20MB 
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일 크기는 20MB 이상 클 수 없습니다",
						HttpStatus.BAD_REQUEST);
			}
		// 서버 컴퓨터에 파일 넣은 디렉토리가 있는지 검사
			String saveDirectory = Define.UPLOAD_FILE_DIRECTORY;
			// 폴더가 없다면 오류 발생(파일 생성시)
			File dir = new File(saveDirectory);
			if(dir.exists() == false) {
				dir.mkdir(); // 폴더가 없으면 폴더 생성
			}
		}
		System.out.println("///////////여기는?");
		// 파일 이름(중복 처리 예방)
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		System.out.println("fileName: " + fileName);
		// C:\\wok_spring\\upload\ab.png
		
		String uploadPath 
			= Define.UPLOAD_FILE_DIRECTORY + File.separator + fileName;
		File destination = new File(uploadPath);
		
		try {
			file.transferTo(destination);
		}catch(IllegalStateException | IOException e){
			e.printStackTrace();
		}
		
		// 객체 상태 변경
		dto.setOriginFileName(file.getOriginalFilename());
		dto.setUploadFileName(fileName);
		
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
		return "redirect:/account/list";
		
	}
	
		//로그아웃 기능 만들기
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
	// http://localhost:80/user/kakao-callback?code="xxxxxxxx"
	@GetMapping("/kakao-callback")
	public String kakaoCallback(@RequestParam String code) {
		System.out.println("code : " + code);
		
		// POST 방식, Header 구성, Body 구성
		// https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api 보면서 개발한 부분
		RestTemplate rt1 = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "b67f0f609e790dc17bea15a6d44db571");
		params.add("redirect_uri", "http://localhost/user/kakao-callback");
		params.add("code", code); //value code는 동적으로 넣어야 한다
		
		// 헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg
			= new HttpEntity<>(params, headers1);
		
		ResponseEntity<OAuthToken> response = rt1.exchange("https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, reqMsg, OAuthToken.class);
		// 위의 과정이 응답받은 것
		
		// 다시 요청하기 -- 인증 토큰 -- 사용자 정보 요청
		RestTemplate rt2 = new RestTemplate();
		// 헤더
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + response.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8" + response.getBody().getAccessToken());
		// 바디 x
		// 결합 --> 요청
		HttpEntity<MultiValueMap<String, String>> kakaoInfo 
			= new HttpEntity<>(headers2);
		// 요청
		ResponseEntity<KakaoProfile> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoInfo, KakaoProfile.class);
		
		System.out.println(response2.getBody());
		
		KakaoProfile kakaoProfile = response2.getBody();
		// 최초 사용자 판단 여부 -- 사용자 username 존재 여부 확인
		// 우리사이트 --> 카카오 
		SignUpFormDTO dto = SignUpFormDTO.builder()
				.username("OAuth_" + kakaoProfile.getProperties().getNickname())
				.fullname("Kakao")
				.password("asd1234")
				.build();
	    User oldUser = userService.readUserByUserName(dto.getUsername());
		if(oldUser == null) {
			userService.createUser(dto);
			///////////////////////////
			oldUser = new User(); 
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
		}
		oldUser.setPassword(null);
		// 로그인 처리
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		return "redirect:/account/list";
	}
}





