package com.kosta.finalproject.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity(name = "BOARD")
@Data
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ", initialValue = 1, allocationSize = 1)

public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
	@Column(name = "BOARD_NO")
	private Long boardNo;

	@Column(name = "MEMBER_ID")
	private String memberId;

	@Column(name = "BOARD_STATUS")
	private int boardStatus;

	@Column(name = "BOARD_TITLE")
	private String boardTitle;

	@Column(name = "BOARD_STARTPOINT")
	private String boardStartpoint;

	@Column(name = "BOARD_ENDPOINT")
	private String boardEndpoint;

	@Column(name = "BOARD_CONTENTS")
	private String boardContents;
	
	@Column(name="BOARD_STARTTIME")
	@Convert(converter=StringToDate.class)
	private String boardStarttime;

	@OneToOne
	@JoinColumn(name="boardNo")
	private Passenger passenger;

}




@Converter
class StringToDate implements AttributeConverter<String, Date> {

	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public Date convertToDatabaseColumn(String attribute) {
		// 2018-06-07T00:00
		Date date = null;
		String st = attribute.replace("T", " ");
		//st = st.concat(":00.0");
		
		try {
			date = (Date) sf.parse(st);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public String convertToEntityAttribute(Date dbData) {
		// YYYY-MM-DD HH:MM:SS
		if (dbData != null) {
			return sf.format(dbData);
		} else {
			return null;
		}
	}

}