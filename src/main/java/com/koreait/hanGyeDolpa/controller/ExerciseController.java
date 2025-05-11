package com.koreait.hanGyeDolpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koreait.hanGyeDolpa.dto.ExerciseRecordRequest;
import com.koreait.hanGyeDolpa.dto.ExerciseRequest;
import com.koreait.hanGyeDolpa.entity.Exercise;
import com.koreait.hanGyeDolpa.repository.ExerciseRepository;
import com.koreait.hanGyeDolpa.service.ExerciseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/exercise")
@Slf4j
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseService eService;
    
    @PostMapping("/save")
    public String saveExerciseRecord(@RequestBody ExerciseRecordRequest request	){
        // 클라이언트에서 전송된 JSON 요청을 ExerciseRecordRequest DTO로 매핑 
    	
    	eService.saveExerciseData(request); // 실제 저장 로직은 서비스에서 처리 
        
        return "redirect:/dashboard"; // 저장 후 대시보드로 이동 
    }
    
    @PostMapping("/records")
    public ResponseEntity<List<Exercise>> getRecords(@RequestBody ExerciseRequest request) {
        // 요청에서 날짜와 사용자 ID를 가져옴
    	String exerciseDate = request.getExerciseDate();
        Long userId = request.getUserId();

        log.info(""+userId+ "---"+exerciseDate);

        List<Exercise> records = exerciseRepository.findByUserNoAndExerciseDate(userId, exerciseDate);
        log.info(""+records);
        return ResponseEntity.ok(records);
    }
    
    
}
