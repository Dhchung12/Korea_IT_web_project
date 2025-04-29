package com.koreait.hanGyeDolpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.koreait.hanGyeDolpa.bean.CommentVO;
import com.koreait.hanGyeDolpa.mapper.CommentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CommentDAO {

	@Autowired
    private CommentMapper commentMapper;

	// 게시글 번호로 댓글 목록 조회 
    public List<CommentVO> getCommentsByBno(Long bno) {
        return commentMapper.getCommentsByBno(bno);
    }

    // 댓글 추가 
    public boolean addComment(CommentVO comment) {
        return commentMapper.insertComment(comment);
    }
    
    // 게시글 번호로 댓글 + 작성자 이름 포함 조회 
    public List<CommentVO> getCommentsByBnoandName(Long bno) {
        return commentMapper.getCommentsByBnoandName(bno);
    }

    // 게시글 번호로 모든 댓글 삭제 
    public int deleteComments(Long bno){
        return commentMapper.removeComment(bno);
    }
}