package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration --> spring boot 설정 클래스이다
@Configuration // IoC 대상 : 2개 이상에 IoC (Bean) 사용
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired // DI
	private AuthInterceptor authInterceptor;
	
	// 요청 올 때 마다 domain URI 검사를 할 예정
	// /account/xxx <- 으로 들어오는 도메인을 다 검사해!
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**")
				.addPathPatterns("/auth/**");
	}
	@Bean // IoC 대상 - 싱글톤 처리
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
}
