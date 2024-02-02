package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDTO;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 대상 
public class UserService {

	// DB 접근
	// 생성자 의존 주입 DI
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/*
	 * 회원 가입 로직 처리
	 * @param SignUpFormDto
	 * return void
	 */
	
	// 회원 가입
	@Transactional  // 트랜 잭션 처리 습관
	public void createUser(SignUpFormDTO dto) {
		
		// 추가 개념 암호화 처리
		
			User user = User.builder()
					.username(dto.getUsername())
					.password(passwordEncoder.encode(dto.getPassword()))
					.fullname(dto.getFullname())
					.build();
		
		int result = userRepository.insert(user);
		if(result != 1) {
			throw new CustomRestfulException("회원 가입 실패", 
						HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * 로그인 처리
	 * @param SignInFromDto
	 * @return User
	 */
	
	public User readUser(SignInFormDto dto) {
		
		// 사용자 username 받아서 정보를 추출
		User userEntity = userRepository.findByUsername(dto.getUsername());
		if(userEntity == null) {
			throw new CustomRestfulException("존재하지 않는 계정입니다", 
					HttpStatus.BAD_REQUEST);
		}
		
		boolean isPwdMatched = 
				passwordEncoder.matches(dto.getPassword(), 
						userEntity.getPassword());
		
		if(isPwdMatched == false) {
			throw new CustomRestfulException("비밀번호가 잘못되었습니다", 
					HttpStatus.BAD_REQUEST);
		}
		return userEntity;
	}
}
