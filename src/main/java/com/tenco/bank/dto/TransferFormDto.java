package com.tenco.bank.dto;

import lombok.Data;

@Data
public class TransferFormDto {

	private Long amount;
	private String wAccountNumber;  // 출금계좌
	private String dAccountNumber; // 입금계좌
	private String password;
	
}
