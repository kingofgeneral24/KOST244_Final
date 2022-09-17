package com.kosta.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.CarInfo;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
	
	CarInfo findByboardNo(Long boardNo);

	void deleteByboardNo(Long boardNo);
}