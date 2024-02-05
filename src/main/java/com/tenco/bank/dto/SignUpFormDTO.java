package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpFormDTO {
	// id -> autoincreament 걸어둠
	private String username;
	private String password;
	private String fullname;
	// 파일 처리
	private MultipartFile customFile; // name 속성 값과 동일 해야 한다.
	
	private String originFileName;
	private String uploadFileName;
	
}
