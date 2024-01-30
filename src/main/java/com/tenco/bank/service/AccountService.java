package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.utils.Define;

@Service //IoC 대상 + 싱글톤으로 관리 됨
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	
	// todo 계좌 번호 중복 확인 예장
	// 사용자 정보 필요 /principalId 접근주체id
	@Transactional //select 구문 제외하고 무조건 트랙젝션 걸기
	public void createAccount(AccountSaveFormDto dto, Integer principalId) {
		
		// 계좌 번호 중복 확인
		if(readAccount(dto.getNumber()) != null) {
			throw new CustomRestfulException(Define.EXIST_ACCOUNT , HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setPassword(dto.getPassword());
		account.setBalance(dto.getBalance());
		account.setUserId(principalId);
		
		
		int resultRowCount =  accountRepository.insert(account);
		if(resultRowCount != 1) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 단일 계좌 검색 기능
	public Account readAccount(String number) {
		return accountRepository.findByNumber(number.trim());
	}
	
	// 계좌 목록 보기 기능
	public List<Account> readAccountListByUserId(Integer principalId) {
		return accountRepository.findAllByUserId(principalId);
		
	
	}
}
