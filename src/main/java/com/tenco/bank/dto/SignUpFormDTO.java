package com.tenco.bank.dto;

import lombok.Data;

@Data
public class SignUpFormDTO {
	// id -> autoincreament 걸어둠
	private String username;
	private String password;
	private String fullname;
	// 파일 처리

}
