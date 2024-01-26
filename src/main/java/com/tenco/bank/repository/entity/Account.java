package com.tenco.bank.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
	
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	//private Integer id;
	//private int number;
	//private int password;
	//private int balance;
	//private int user_id;
	//private Timestamp created_at;

	
	// 출금 기능
	public void withdraw(Long amount) {
		// 방어적 코드 작성 - todo 마이너스값은 안돼
		this.balance -= amount;
	}
	// 입금 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}
	// 패스워트 체크
	// 잔액 여부 확인 기능
	// 계좌 소유자 확인 기능
}
