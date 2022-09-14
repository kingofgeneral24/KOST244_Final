package com.kosta.finalproject;

import java.sql.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.service.MemberService;

import oracle.sql.DATE;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
	@Autowired
	private MemberService memberService;

	public MemberDTO newMember(int i) {
		// Date date = new Date(2022, 8, 30)
		MemberDTO member = new MemberDTO("테스트용 이메일" + i, "테스트용 비밀번호" + i, "테스트용 이름" + i, Date.valueOf("2022-08-22"),
				"테스트용 전화번호" + i, "테스트용 운전면허증" + i, 7 + i);
		return member;

	}

	@Test
	@Transactional
	// test section에서 transational을 붙여주면 자동 rollback
	@Rollback(value = true)
	@DisplayName("회원가입 테스트")
	public void memberSaveTest() {
		Long savedId = memberService.save(newMember(1));
		MemberDTO memberDTO = memberService.findById(savedId);
		assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
	}

	@Test
	@Transactional
	@Rollback(value = true)
	@DisplayName("로그인 테스트")
	public void loginTest() {
		final String memberEmail = "테스트용 이메일1";
		final String memberPassword = "테스트용 이메일1";
		String memberName = "1";
		Date date = new Date(2022,01,01);
		Date memberBirth = date;
		String memberMobile= "1234";
		String memberLicense="1234";
		int memberLike =1;
	
		MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword,memberName, memberBirth, memberMobile, memberLicense, memberLike);
		// Long savedId = memberService.save(memberDTO);
		// 로그인 객체 생성 후 로그인
		MemberDTO loginMemberDTO = new MemberDTO();
		loginMemberDTO.setMemberEmail(memberEmail );
		loginMemberDTO.setMemberPassword(memberPassword);
		// loginMemberDTO.setMemberPassword(memberBirth);
		// loginMemberDTO.setMemberPassword(memberMobile);
		// loginMemberDTO.setMemberPassword(memberLicense);
		// loginMemberDTO.setMemberPassword(memberLikee);

		MemberDTO loginResult = memberService.login(loginMemberDTO);
		// 로그인 결과가 not null 이면 테스트 통과
		assertThat(loginResult).isNotNull();
	}

	@Test
	@DisplayName("회원 데이터 저장")
	public void memberSave() {
		IntStream.rangeClosed(1, 5).forEach(i -> {
			memberService.save(newMember(i));
		});

	}

	@Test
	@Transactional
	@Rollback(value = true)
	@DisplayName("회원 삭제 테스트")
	public void memberDeleteTest() {
		/**
		 * 신규 회원 등록 삭제 처리 해당 회원으로 조회시 null이면 통과
		 * 
		 */

		// jpa장점:db갈아끼우기가 쉬움
		Long savedId = memberService.save(newMember(999));
		memberService.delete(savedId);
		assertThat(memberService.findById(savedId)).isNull();

	}

}
