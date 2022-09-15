package com.kosta.finalproject.dto;

import lombok.Data;

@Data
public class PagingDTO {
	private String id;
	private String title;
	public PagingDTO(String id, String title) {
		this.id = id;
		this.title = title;
	}
}
