package com.koreait.hanGyeDolpa.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login/*")
public class KakaoLoginPageController {
	
	@Value("${kakao.client-id}")
    private String client_id;

    @Value("${kakao.redirect-uri}")
    private String redirect_uri;

    // 로그인 버튼 페이지 요청 처리 
    @GetMapping("page")
    public String loginPage(Model model, RedirectAttributes rttr) {
    	// 카카오 로그인 URL을 모델에 담아 뷰에서 버튼 클릭 시 이동 
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        // 바로 로그인 페이지 이동시 빈 메시지 출력 
        if(rttr.getFlashAttributes().isEmpty()) {
        	rttr.addFlashAttribute("msg", "");
        }
        
        // 로그인 후 로그인 html 페이지 반환 
        return "Sample_kakaoLogin";
    }
}
