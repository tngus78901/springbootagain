package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.entity.Account;

@Mapper  // mapper framework쓰려면 반드시!
public interface AccountRepository {
	
	public int insert(Account account);
	public int updateById(Account account);
	public int deleteById(Integer id);
	
	// 계좌 조회 - 1 유저, N계좌
	public List<Account> findAllByUserId();
	public Account findByNumber(Integer id);
	


}
