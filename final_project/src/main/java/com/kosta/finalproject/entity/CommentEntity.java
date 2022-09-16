//package com.kosta.finalproject.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.apache.catalina.User;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import groovy.transform.builder.Builder;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Table(name="comments")
//@Entity
//public class CommentEntity {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(columnDefinition = "TEXT", nullable = false)
//	private String comment; //댓글 내용
//	
//	@Column(name= "created_date")
//	@CreatedDate
//	private String createdDate;
//	
//	@Column(name="modified_date")
//	@LastModifiedDate
//	private String ModifiedDate;
//	
//	@ManyToOne
//	@JoinColumn (name = "post_id")
//	private Posts posts;
//	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user; //작성자
//	
//
//}
