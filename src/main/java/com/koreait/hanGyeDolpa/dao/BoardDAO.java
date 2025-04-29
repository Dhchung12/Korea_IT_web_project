package com.koreait.hanGyeDolpa.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.koreait.hanGyeDolpa.bean.BoardVO;
import com.koreait.hanGyeDolpa.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BoardDAO {
	
	@Autowired
	private BoardMapper mapper;
		
	// 전체 게시글 조회 
	public List<BoardVO> getList() { return mapper.getList(); }
	
	// 검색 조건에 따라 필터링된 게시글 목록 조회 
	public List<BoardVO> getListWithKey(String type, String keyword){
		return mapper.getListWithKey(type, keyword);
	}
	
	// 게시글 + 작성자 이름 포함 목록 조회 
	public List<BoardVO> getUserNameList(){
		return mapper.getUserNameList();
	}
	
	// 검색 조건에 따라 필터링된 게시글 + 작성자 이름 포함 목록 조회 
	public List<BoardVO> getUserNameListWithKey(String type, String keyword){
		return mapper.getUserNameListWithKey(type, keyword);
	}
	
	// 게시글 등록 처리 
	public int register(BoardVO board) {
		int cnt  = mapper.insertSelectKey(board);
//		int cnt = mapper.insert(board);
		
		return cnt;
	}
	
	// 게시글 상세 조회 
	public BoardVO read(Long bno) {
		return mapper.get(bno);
	}
	
	// 게시글 수정 처리 
	public int modify(BoardVO board) {
		return mapper.update(board);
	}
	
	// 게시글 삭제 처리 
	public int remove(Long bno) {
		return mapper.delete(bno);
	}
	
	// 게시글 조회수 증가 
	public void updateViewCount(Long bno) {
		mapper.updateViewCount(bno);
	}
	
	// 게시글 작성자 ID 조회 
	public Long getUserIDinBoard(Long bno) {
		return mapper.getUserIDinBoard(bno);
	}
	
	// 게시글 + 작성자 이름 포함하여 조회 
	public BoardVO getAllDataAndUserName(Long bno) {
		return mapper.getAllDataAndUserName(bno);
	}
	
}