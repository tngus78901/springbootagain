package com.tenco.bank.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfulException;

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
	// 패스워드 체크
	public void checkPassword(String password) {
		if(this.password.equals(password) ==  false) {
			throw new CustomRestfulException("계좌 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
		}
	}
	// 잔액 여부 확인 기능
	public void checkBalance(Long amount) {
		if(this.balance < amount){
			throw new CustomRestfulException("출금 잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
	}
	// 계좌 소유자 확인 기능
	public void checkOwner(Integer principalId) {
		if(this.userId != principalId) {
			throw new CustomRestfulException("계좌 소유자가 아닙니다.", HttpStatus.BAD_REQUEST);
		}
}
	//포메터 기능 만들어주세요
	public String formatBalance()	{
		DecimalFormat df = new DecimalFormat("#,###");
		String formaterNumber = df.format(balance);
		// 1000 --> 1000
		return formaterNumber + "원";
	}
}
