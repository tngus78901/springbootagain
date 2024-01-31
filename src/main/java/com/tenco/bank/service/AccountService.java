package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.History;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.utils.Define;

@Service //IoC 대상 + 싱글톤으로 관리 됨
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
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
	// 출금 기능 만들기
	// 1. 계좌 존재 여부 확인 -- select
	// 2. 본인 계좌 여부 확인 -- select
	// 3. 계좌 비번 확인
	// 4. 잔액 여부 확인
	// 5. 출금 처리 ---> update
	// 6. 거래 내역 등록 --> insert
	// 7. transaction 처리
	@Transactional
	public void updateAccountWithdraw(WithdrawFormDto dto, Integer principalId) {
		// 1
		Account accountEntity = accountRepository.findByNumber(dto.getWAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 2.
		accountEntity.checkOwner(principalId);
		//if(accountEntity.getUserId() != principalId) {
			//throw new CustomRestfulException("본인 소유 계좌가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		//}
		// 3. (String) 불변		
		accountEntity.checkPassword(dto.getWAccountPassword());
		// 4
		accountEntity.checkBalance(dto.getAmount());
		// 5  --> 출금 기능(Account) --> 객체 상태값 변경
		accountEntity.withdraw(dto.getAmount());
		accountRepository.updateById(accountEntity);
		// 6 
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 입금 
	public void updateAccountDeposit(DepositFormDto dto, Integer principalId) {
		// 1
		Account accountEntity = accountRepository.findByNumber(dto.getDAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 2.
		accountEntity.checkOwner(principalId);
		//if(accountEntity.getUserId() != principalId) {
			//throw new CustomRestfulException("본인 소유 계좌가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		//}
		// 3. (String) 불변		
		accountEntity.checkPassword(dto.getDAccountPassword());
		// 4
		accountEntity.checkBalance(dto.getAmount());
		// 5  --> 출금 기능(Account) --> 객체 상태값 변경
		accountEntity.deposit(dto.getAmount());
		accountRepository.updateById(accountEntity);
		// 6 
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setDBalance(accountEntity.getBalance());
		history.setWBalance(null);
		history.setDAccountId(accountEntity.getId());
		history.setWAccountId(null);
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
