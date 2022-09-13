package com.kosta.finalproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity(name ="CAR_INFO")
@Data

public class CarInfo {
	@Column(name = "BOARD_NO")
	private Long boardNo;
	@Column(name = "CAR_TYPE")
	private String carType;
	@Column(name = "MAXSEAT")
	private Long maxSeat;
	@Column(name = "CAR_YEAR")
	private Long carYear;
}
