package com.koreait.hanGyeDolpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.koreait.hanGyeDolpa.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
	// 사용자별 + 날짜별 운동 조회 
	List<Exercise> findByUserNoAndExerciseDate(Long userNo, String exerciseDate);
	
	// 특정 날짜 운동 1건 조회 
	Exercise findByExerciseDate(String exerciseDate);

	// 날짜 범위 내 운동 데이터 전체 조회 (대시보드)
	@Query("SELECT e FROM Exercise e WHERE e.exerciseDate BETWEEN :startDate AND :endDate AND e.userNo = :userNo")
	List<Exercise> findAllByDateRange(
		@Param("userNo") Long userNo,
	    @Param("startDate") String startDate,
	    @Param("endDate") String endDate
	);
}