package com.kosta.finalproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	//select * from member_table where memberEmail =? 
	//리턴타입: MemberEntity
	//매개변수: MemberId(String)
	Optional<MemberEntity> findByMemberId(String memberId);

	// 엔티티 컬럼명 And 키워드로 조회조건을 추가 할 수 있다.
	//아이디 찾기 , 메일과 이름으로 조회 
	Optional<MemberEntity> findByMemberEmailAndMemberName(String memberEmail, String memberName);

	//비밀번호 찾기 , 아이디와 이름으로 조회
	Optional<MemberEntity> findByMemberIdAndMemberName(String memberId, String memberName);	


}