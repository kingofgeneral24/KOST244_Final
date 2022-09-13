<<<<<<< HEAD
package com.kosta.finalproject.dto;


import java.sql.Date;

import com.kosta.finalproject.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberDTO {
		private Long memberNo;
		private String memberId;
		private String memberEmail;
		private String memberPassword;
		private String memberName;
		private String memberBirth;
		private String memberMobile;
		private String memberLicense;
		private int memberLike;
		
		
		
		public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setMemberNo(memberEntity.getMemberNo());
			memberDTO.setMemberId(memberEntity.getMemberId());
			memberDTO.setMemberEmail(memberEntity.getMemberEmail());
			memberDTO.setMemberPassword(memberEntity.getMemberPassword());
			memberDTO.setMemberName(memberEntity.getMemberName());
			memberDTO.setMemberBirth(memberEntity.getMemberBirth());
			memberDTO.setMemberMobile(memberEntity.getMemberMobile());
			memberDTO.setMemberLicense(memberEntity.getMemberLicense());
			memberDTO.setMemberLike(memberEntity.getMemberLike());
			return memberDTO;
		
	
		}



		public MemberDTO(String memberEmail, String memberPassword, String memberName, String memberBirth,
				String memberMobile, String memberLicense, int memberLike) {
			super();
			this.memberEmail = memberEmail;
			this.memberPassword = memberPassword;
			this.memberName = memberName;
			this.memberBirth = memberBirth;
			this.memberMobile = memberMobile;
			this.memberLicense = memberLicense;
			this.memberLike = memberLike;
		}	
}


=======
package com.kosta.finalproject.dto;


import java.sql.Date;

import com.kosta.finalproject.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberDTO {
		private Long memberNo;
		private String memberId;
		private String memberEmail;
		private String memberPassword;
		private String memberName;
		private String memberBirth;
		private String memberMobile;
		private String memberLicense;
		private int memberLike;
		
		
		
		public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setMemberNo(memberEntity.getMemberNo());
			memberDTO.setMemberId(memberEntity.getMemberId());
			memberDTO.setMemberEmail(memberEntity.getMemberEmail());
			memberDTO.setMemberPassword(memberEntity.getMemberPassword());
			memberDTO.setMemberName(memberEntity.getMemberName());
			memberDTO.setMemberBirth(memberEntity.getMemberBirth());
			memberDTO.setMemberMobile(memberEntity.getMemberMobile());
			memberDTO.setMemberLicense(memberEntity.getMemberLicense());
			memberDTO.setMemberLike(memberEntity.getMemberLike());
			return memberDTO;
		
	
		}



		public MemberDTO(String memberEmail, String memberPassword, String memberName, String memberBirth,
				String memberMobile, String memberLicense, int memberLike) {
			super();
			this.memberEmail = memberEmail;
			this.memberPassword = memberPassword;
			this.memberName = memberName;
			this.memberBirth = memberBirth;
			this.memberMobile = memberMobile;
			this.memberLicense = memberLicense;
			this.memberLike = memberLike;
		}	
}


>>>>>>> main
