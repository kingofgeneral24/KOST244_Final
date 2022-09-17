//package com.kosta.finalproject.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//
//import lombok.Data;
//
//@Entity(name = "test_comment")///////////////////////////////////
//@Data
//@SequenceGenerator(name = "COMMENT_NO", 
//sequenceName = "COMMENT_NO", 
//initialValue = 1, 
//allocationSize = 1)
//public class Comment {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="COMMENT_NO") 
//	@Column(name = "BOARD_NO")
//	private Long boardNo;
//	@Column(name = "COMMENT_NO")
//	private Long commentNo;
//	@Column(name = "PARENT_NO")
//	private Long parentNo;
//	@Column(name = "COMMENT_CONTENTS")
//	private String commentContents;
//}
