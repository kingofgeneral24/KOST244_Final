package com.kosta.finalproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity(name = "PASSENGER_TABLE")
@Data
@SequenceGenerator(name = "PASSENGER_SEQ_GENERATOR", sequenceName = "PASSENGER_SEQ", initialValue = 1, allocationSize = 1)
public class Passenger{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSENGER_SEQ_GENERATOR")
	@Column(name="PASSENGER_TABLE_NO")
	private Long passengerboardNo;
	
	@Column(name="BOARD_NO")
	private Long boardNo;
	
	@Column(name="MEMBER_ID")
	private String passengerId;
	
	
	@Column(name="PASSENGER_CHECK")
	private String passengerCheck = "대기중";

}