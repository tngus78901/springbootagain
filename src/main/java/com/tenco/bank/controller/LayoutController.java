package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LayoutController {
	
	@GetMapping("/layout/banksite")
	public String bankSite() {   // 인증검사
		
		return "layout/banksite";
	}
	
	
	
}

