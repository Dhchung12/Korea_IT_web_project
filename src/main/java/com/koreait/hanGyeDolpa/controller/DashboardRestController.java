package com.koreait.hanGyeDolpa.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koreait.hanGyeDolpa.dto.checkDataForCalendar;
import com.koreait.hanGyeDolpa.service.DashboardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class DashboardRestController {

	@Autowired
	private DashboardService dashbService;
	
	// 운동 기록 유무를 날짜별로 조회 
	@GetMapping("/getCalendarData")
	public List<checkDataForCalendar> getCalendarData(
										@RequestParam String startDate,
										@RequestParam String endDate,
										HttpSession session
										){
		
		Long userNo = (Long) session.getAttribute("uNo");
		
		return dashbService.getCalendarData(startDate, endDate, userNo);
	}
	
	// 운동 통계를 운동 장소별, 단계별 형태로 조회 
	@GetMapping("/getComboChartData")
	public ResponseEntity<Map<String, Map<Integer, Integer>>> getComboChartData(
										@RequestParam String startDate,
										@RequestParam String endDate,
										HttpSession session
										){
		Long userNo = getUserNoInHttpSession(session);
		Map<String, Map<Integer, Integer>> totalValue = dashbService.getComboData(startDate, endDate, userNo);
		
		if(totalValue == null || totalValue.isEmpty()) {
		}
		
		return ResponseEntity.ok(totalValue);
	}
	
	// 총시간, 칼로리, 횟수 등 요약 데이터 조회 
	@GetMapping("/getTotalTimeData")
	public Map<String, Map<String, Integer>> getTotalTimeData(
										@RequestParam String startDate,
										@RequestParam String endDate,
										HttpSession session
										){
		Long userNo = getUserNoInHttpSession(session);
		return dashbService.getTotlaData(startDate, endDate, userNo);
		
	}
	 // 가장 높은 운동 점수 기등 등 조회 
	@GetMapping("/getHighstScoreData")
	public Map<String, Integer> getHighstScoreData(
										@RequestParam String startDate,
										@RequestParam String endDate,
										HttpSession session
										){
		Long userNo = getUserNoInHttpSession(session);
		return dashbService.getHighstScore(startDate, endDate, userNo);
	}
	
	@GetMapping("/setSessionStorage")
	public String setSessionStorage(HttpSession session) {
		
		// 사용자 확인(서버 -> 클라이어느 세션)
	    Long uNoValue = getUserNoInHttpSession(session);
	    return uNoValue != null ? uNoValue.toString() : "";
	}
	
	private Long getUserNoInHttpSession(HttpSession session) {
		
		Long userNo = (Long) session.getAttribute("uNo");
		
		return userNo;
	}
}
