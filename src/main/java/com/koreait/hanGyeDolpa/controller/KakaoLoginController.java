package com.koreait.hanGyeDolpa.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koreait.hanGyeDolpa.service.LoginServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

	@Autowired
	private final LoginServiceImpl kakaoService;
	
	// 카카오 인증 완료 후 리디렉션되는 콜백 URL
	// 인가 코드를 받아서 토큰 요청, 사용자 정보 조회 수행 
    @GetMapping("/callbacks")
    public ResponseEntity<Void> callback(@RequestParam("code") String code, HttpSession session) throws IOException {
        
    	// 인가 코드로 카카오에 액세스 토큰 요
    	String accessToken = kakaoService.getAccessTokenFromKakao(code);
    	
    	// 세션에 액세스 토큰 저장 
    	session.setAttribute("aT", accessToken);
    	
    	
        // 액세스 토큰으로 사용자 정보 요청 및 세션 저장 
        kakaoService.getUserInfo(accessToken, session);

        // 로그인 후 메인 페이지로 이동 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
        
    }
    
    @GetMapping("/logoutCallback")
    public ResponseEntity<Void> logoutCallback(HttpSession session) {

        //응답 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}