package com.kosta.finalproject.dto;

import java.time.LocalDateTime;

import com.kosta.finalproject.entity.AdminEntity;

import groovy.transform.ToString;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class AdminDTO {
		private Long id;
		private String writer;
		private String title;
		private String content;
		private LocalDateTime createdDate;
		private LocalDateTime modifiedDate;
		
		public AdminEntity toEntity() {
			AdminEntity adminEntity = AdminEntity.builder()
					.id(id)
					.writer(writer)
					.title(title)
					.content(content)
					.build();
			return adminEntity;
			
		}
		
		@Builder
		public AdminDTO(Long id, String title, String contetn, String writer, LocalDateTime createDate, LocalDateTime modifiDate) {
									this.id = id;
									this.writer = writer;
									this.title = title;
									this.content = content;
									this.createdDate = createdDate;
									this.modifiedDate = modifiedDate;
		}

}
