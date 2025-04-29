package com.koreait.hanGyeDolpa.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.hanGyeDolpa.bean.AttachFileVO;
import com.koreait.hanGyeDolpa.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService bService;

	// 게시글 목록 조회 (검색 포함) 
	@GetMapping("list")
	public void list(String type, String keyword, Model model) {
		log.info("Type -> " + type + " -> Keyword -> "+ keyword);
		if(keyword == null) {
			// 검색어가 없는경우 전체 게시글 목록 조회 
			model.addAttribute("list", bService.getUserNameList());
		}
		else {
			// 검색어가 있는 경우 type에 따라 필터링된 게시글 목록 조회 
			model.addAttribute("list",bService.getUserNameListWithKey(type, keyword));
		}
		
	}
	
	// localhost:10000/board/read?=N을 호출했을 때 페이지 
	// 게시글 상세 조회 및 조회수 증가 
	@RequestMapping("read")
	public void read(Long bno, Model model) {
		// 조회수 증가
		bService.updateViewCount(bno);
		// 게시글 정보 가져오기
		model.addAttribute(bService.readBoardService(bno, model));
	}
	
	// 게시글 첨부파일 목록 조회 (JSON 반환)
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AttachFileVO> getAttachList(Long bno){
		// 특정 게시글의 첨부파일 리스트 반환 
		return bService.getAttachFileList(bno);
	}
	
}
