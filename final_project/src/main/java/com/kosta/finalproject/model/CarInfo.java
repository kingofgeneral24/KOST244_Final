package com.kosta.finalproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity(name ="CAR_INFO")
@Data

public class CarInfo {
   @Id
   @Column(name = "BOARD_NO")
   private Long boardNo;
   @Column(name = "CAR_TYPE")
   private String carType;
   @Column(name = "MAXSEAT")
   private Long maxSeat = 1L;
   @Column(name = "CAR_YEAR")
   private Long carYear;
}