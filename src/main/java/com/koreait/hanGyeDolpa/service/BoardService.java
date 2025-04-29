package com.koreait.hanGyeDolpa.service;

import java.util.List;

import org.springframework.ui.Model;

import com.koreait.hanGyeDolpa.bean.AttachFileVO;
import com.koreait.hanGyeDolpa.bean.BoardVO;
import com.koreait.hanGyeDolpa.bean.CommentVO;

import jakarta.servlet.http.HttpSession;

public interface BoardService {
	
	// 게시글 작성자와 현재 로그인 사용자 일치 여부 확인 
	public boolean checkUserRight(Long bNo, HttpSession session);
	
	// 게시글 상세 조회 + Model에 데이터 추가 
	public Model readBoardService(Long bno, Model model);
	
	// 게시글 등록 메시지 생성 및 처리 
	public String makeRegiMsg(BoardVO vo);
	
	// 게시글 수정 메시지 생성 및 처리 
	public String makeModifyMsg(BoardVO vo, HttpSession session);
	
	// 게시글 삭제 메시지 생성 및 처리 
	public String makeDeleteMsg(Long bNo, HttpSession session);
	
	// 댓글 등록 메시지 생성 및 처리 
	public String makeCommentMsg(CommentVO comment);
	
	// 전체 게시글 조회 
	public List<BoardVO> getUserNameList();
	
	// 검색으로 필터링된 게시글 조회 
	public List<BoardVO> getUserNameListWithKey(String type, String keyword);
	
	// 게시글 조회수 증가 
	public void updateViewCount(Long bno);
	
	// 첨부파일 목록 조회 
	public List<AttachFileVO> getAttachFileList(Long bno);


}
