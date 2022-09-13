<<<<<<< HEAD
package com.kosta.finalproject.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosta.finalproject.dto.MemberDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "member_table")
public class MemberEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "member_no")
	private Long memberNo;

	@Column(name="member_id", length = 255)
	private String memberId;
	
	@Column(name="member_email", length = 50)
	private String memberEmail;
	
	@Column(name="member_password", length = 20)
	private String memberPassword;
	
	@Column(name="member_name", length = 20)
	private String memberName;
	
	@Column(name="member_birth", length = 20)
	private String memberBirth;
	
	@Column(name="member_mobile", length = 30)
	private String memberMobile;
	
	@Column(name="member_license", length = 30)
	private String memberLicense;
	
	@Column(name="member_like", length = 10)
	private int memberLike;
	
	public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setMemberNo(memberDTO.getMemberNo());
		memberEntity.setMemberId(memberDTO.getMemberId());		
		memberEntity.setMemberEmail(memberDTO.getMemberEmail());
		memberEntity.setMemberPassword(memberDTO.getMemberPassword());
		memberEntity.setMemberName(memberDTO.getMemberName());
		memberEntity.setMemberBirth(memberDTO.getMemberBirth());
		memberEntity.setMemberMobile(memberDTO.getMemberMobile());
		memberEntity.setMemberLicense(memberDTO.getMemberLicense());
		memberEntity.setMemberLike(memberDTO.getMemberLike());
		return memberEntity;
		
	
}
	
}


=======
package com.kosta.finalproject.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosta.finalproject.dto.MemberDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "member_table")
public class MemberEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "member_no")
	private Long memberNo;

	@Column(name="member_id", length = 255)
	private String memberId;
	
	@Column(name="member_email", length = 50)
	private String memberEmail;
	
	@Column(name="member_password", length = 20)
	private String memberPassword;
	
	@Column(name="member_name", length = 20)
	private String memberName;
	
	@Column(name="member_birth", length = 20)
	private String memberBirth;
	
	@Column(name="member_mobile", length = 30)
	private String memberMobile;
	
	@Column(name="member_license", length = 30)
	private String memberLicense;
	
	@Column(name="member_like", length = 10)
	private int memberLike;
	
	public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setMemberNo(memberDTO.getMemberNo());
		memberEntity.setMemberId(memberDTO.getMemberId());		
		memberEntity.setMemberEmail(memberDTO.getMemberEmail());
		memberEntity.setMemberPassword(memberDTO.getMemberPassword());
		memberEntity.setMemberName(memberDTO.getMemberName());
		memberEntity.setMemberBirth(memberDTO.getMemberBirth());
		memberEntity.setMemberMobile(memberDTO.getMemberMobile());
		memberEntity.setMemberLicense(memberDTO.getMemberLicense());
		memberEntity.setMemberLike(memberDTO.getMemberLike());
		return memberEntity;
		
	
}
	
}


>>>>>>> main
