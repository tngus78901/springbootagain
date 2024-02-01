package com.tenco.bank.dto;

import lombok.Data;

@Data
public class TransferFormDto {

	private Long amount;
	private String tAccountNumber;  // 출금계좌
	private String tAccountPassword; // 입금계좌
	private String password;
	
}
