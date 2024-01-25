package com.tenco.bank.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // bulder pattern 으로 만들고 싶다면 
public class User {
	

	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private Timestamp createdAt;
}
