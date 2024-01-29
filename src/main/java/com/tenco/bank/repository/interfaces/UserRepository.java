package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.dto.SignUpFormDTO;
import com.tenco.bank.repository.entity.User;

//interface + xml 연결
@Mapper
public interface UserRepository {

	public int insert(SignUpFormDTO user);//entity user
	public int updateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id); // 관리자
	public List<User> findAll();
	
	// 사용자 username으로 존재 여부 확인
	public User findByUsername(User user);
	public User findByUsernameAndPassword(User user);
	
	
}
