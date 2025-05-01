package com.koreait.hanGyeDolpa.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/logout/*")
@Slf4j
public class KakaoLogoutPageController {

	@Value("${kakao.client-id}")
    private String client_id;

    @Value("${kakao.logout.redirect-uri}")
    private String redirect_uri;

    // 로그아웃 페이지 진입 시 호출 
    @GetMapping("page")
    public String logOutPage(Model model, HttpSession session) {
    	
    	// 카카오 로그아웃 URL 생성 (카카오에서 로그아웃)
        String location = "https://kauth.kakao.com/oauth/logout?client_id="+client_id+"&logout_redirect_uri="+redirect_uri;
        
        // 뷰에서 사용할 수 있도록 로그아웃 URL을 모델에 적재 
        model.addAttribute("location", location);
        String akey = session.getAttribute("aT").toString();
        
		// 사용자 번호를 초기화하여 비로그인으로 변경 
		session.setAttribute("uNo", 0L);
		Long uNo = (Long) session.getAttribute("uNo");
		
		log.info("로그아웃이 완료되었습니다 -> 유저번회: " + uNo);
        
		// 로그아웃 후 로그아웃 html 페이지 반환 
        return "Sample_kakaoLogout";
    }
}