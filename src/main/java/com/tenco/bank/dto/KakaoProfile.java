package com.tenco.bank.dto;

import lombok.Data;


@Data
public class KakaoProfile {

    public Long id;
    public String connectedAt;
    public Properties properties;
    public KakaoAccount kakaoAccount;
    
    // 본인만의 규칙과 감이 있다면 내부클래스 써도 된다
}