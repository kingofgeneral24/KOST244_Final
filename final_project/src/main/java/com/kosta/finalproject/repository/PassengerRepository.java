package com.kosta.finalproject.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.model.Passenger;
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	//Passenger findByboardNo(Long boardNo);
	List<Passenger> findByboardNo(Long boardNo);
	
	Passenger findByBoardNoAndPassengerId(Long boardNo, String passengerId);
	
	void deleteBypassengerboardNo(Long passengerboardNo);
//	List<Board> findByboardStatus(int status);
	
	List<Passenger> findByBoardNoAndPassengerCheck(Long boardNo, String passengerCheck);
	
	
}