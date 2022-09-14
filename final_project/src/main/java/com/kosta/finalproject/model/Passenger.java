package com.kosta.finalproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity(name = "PASSENGER_TABLE")
@Data
public class Passenger {
	
	@Column(name="BOARD_NO")
	private Long boardNo;
	
	@Column(name="MEMBER_ID")
	private String memberId;

}
