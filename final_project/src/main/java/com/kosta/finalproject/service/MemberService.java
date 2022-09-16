package com.kosta.finalproject.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.entity.MemberEntity;
import com.kosta.finalproject.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Long save(MemberDTO memberDTO) {
		
		log.info("1. 암호화 전 비밀번호는 ? : "+memberDTO.getMemberPassword().toString());
		
		// 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
		String encodePw = passwordEncoder.encode(memberDTO.getMemberPassword());
		
		log.info("2. 암호화 후 비밀번호는 ? : "+encodePw.toString());
		
		memberDTO.setMemberPassword(encodePw);
		
		//생년월일의 값이 있을때만 생년월일의 하이픈("-") 을 없앰
		if(!"".equals(memberDTO.getMemberBirth().toString())){

			String memberBirth = memberDTO.getMemberBirth().toString().replace("-", "");
			log.info("memberBirth : "+memberBirth);
			memberDTO.setMemberBirth(memberBirth);;
		
		}
		
		//MemberEntity memberEntity = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)); 						
		MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
		
		Long savedId = memberRepository.save(memberEntity).getMemberNo();
		return savedId;
		
	}

	public MemberDTO login(MemberDTO memberDTO) {
		
		String pw = memberDTO.getMemberPassword();
		
		/**
		 * login.html에서 아이디, 비번을 받아오고
		 * DB 로부터 해당 이메일의 정보를 가져와서
		 * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
		 * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
		 */
		// return false;
		//memberDTO 로그인페이지에서 입력한값
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());	
		
		
		log.info("optionalMemberEntity >>>>>>>>>>>>>>>>> "+optionalMemberEntity.toString());
		
		log.info("1. 로그인 페이지에서 입력한 비밀번호 값 : "+pw);
	
		if (optionalMemberEntity.isPresent()) {

			//DB에서 조회한 회원정보
			MemberEntity loginEntity = optionalMemberEntity.get();

			log.info("2. 회원 테이블에서 조회한 암호화한 값 : "+loginEntity.getMemberPassword());
			
			//내가 입력한 평문 비밀번호랑  회원정보에있는 비밀번호(암호화한것)을 비교함
			if (passwordEncoder.matches(pw, loginEntity.getMemberPassword())) {
				
				return MemberDTO.toMemberDTO(loginEntity);
			
			} else {
				
				return null;
			}
			
		}else {
			
			return null;
		
		}
	}

	/*회원 아이디로 회원정보 조회하기*/
	public MemberDTO findByMemberId(String memberId) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
	 
		if (optionalMemberEntity.isPresent()) {
			//	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		 MemberEntity memberEntity = optionalMemberEntity.get();
		 MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
		 return memberDTO;
		} else {
			return null;
		}
	}

	public List<MemberDTO> findAll() {
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		List<MemberDTO> memberDTOList = new ArrayList<>();
		for (MemberEntity member: memberEntityList) {
			//MemberDTO memberDTO = MemberDTO.toMemberDTO(member);
			//memberDTOList.add(memberDTO);
			memberDTOList.add(MemberDTO.toMemberDTO(member));
		}
		return memberDTOList;
	}

	public void delete(Long memberNo) {
	
		memberRepository.deleteById(memberNo);

	}
	
	
	/*아이디 찾기 > 메일주소, 이름으로 찾는다. */
	public MemberDTO findByMemberEmailAndMemberName(MemberDTO memberDTO) {
		
		String memberEmail = memberDTO.getMemberEmail();
		String memberName = memberDTO.getMemberName();
		
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberName(memberEmail, memberName);
	 
		if (optionalMemberEntity.isPresent()) {
			
			//	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());
			
			MemberEntity memberEntity = optionalMemberEntity.get();

			MemberDTO resultDTO = MemberDTO.toMemberDTO(memberEntity);

			return resultDTO;
		
		} else {
			return null;
		}
	}

	/*비밀번호 찾기 >아이디, 이름으로 찾는다. */
	public String tempPwUpdate(MemberDTO memberDTO) {
		
		//임시비밀번호 변수 생셩
		String tempPw = "";
		
		String memberId = memberDTO.getMemberId();
		String memberName = memberDTO.getMemberName();
		
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberIdAndMemberName(memberId, memberName);
		
		//아이디와 이름으로 회원정보가 조회가 된다
		if (optionalMemberEntity.isPresent()) {

			MemberEntity memberEntity = optionalMemberEntity.get();
			
			//랜덤 비밀번호 생성
			tempPw = getRamdomPassword(8);

			log.info("1. 암호화 전 비밀번호는 ? : "+tempPw);
			
			// 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
			String encodePw = passwordEncoder.encode(tempPw);
			
			//임시비밀번호 dto에 set
			memberEntity.setMemberPassword(encodePw);
				
			Long savedId = memberRepository.save(memberEntity).getMemberNo();
				
			log.info("2. 업데이트 실행한 MEMBER_NO :  "+savedId);
			
			return tempPw;
		
		}else {	

			return "";

		}
	}
	
	/*임시비밀번호 발급 로직*/
    public String getRamdomPassword(int size) {
    	
    	char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    /*회원 번호로 회원정보 조회하기*/
    public MemberDTO findByMemberNo(Long memberNo) {
    	
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberNo);
	 
		if (optionalMemberEntity.isPresent()) {
			//	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		 MemberEntity memberEntity = optionalMemberEntity.get();
		 MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
		 return memberDTO;
		} else {
			return null;
		}

	}

    /*기존 비밀번호 검증*/
    public String pwChk(String inputPw, String nowPw) {
    	//입력한 비밀번호랑 , 로그인정보의 비밀번호가 같으면 success 문자열을 반환
    	if (passwordEncoder.matches(inputPw, nowPw)) {
    		return "success";
    	}else {
    		return "fail";
    	}
    }

    /*비밀번호 변경한다. */
	public Long updatePassword(MemberDTO memberDTO) {
		
		Long memberNo = memberDTO.getMemberNo();
		
		String beforePw = memberDTO.getMemberPassword();

		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberNo);
		 
		//아이디와 이름으로 회원정보가 조회가 된다
		if (optionalMemberEntity.isPresent()) {

			MemberEntity memberEntity = optionalMemberEntity.get();
			
			// 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
			String encodePw = passwordEncoder.encode(beforePw);
			
			//dto에 set
			memberEntity.setMemberPassword(encodePw);
				
			//기존회원번호가 있기때문에 업데이트를 수행함
			Long savedId = memberRepository.save(memberEntity).getMemberNo();
			
			return savedId;
		
		}else {	

			return null;

		}
	}

	 /*회원정보 변경한다. */
	public Long updateMember(MemberDTO memberDTO) {
		
		Long memberNo = memberDTO.getMemberNo();
		
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberNo);
		 
		//아이디와 이름으로 회원정보가 조회가 된다
		if (optionalMemberEntity.isPresent()) {

			MemberEntity memberEntity = optionalMemberEntity.get();
			
			//dto에서 값을 꺼내어 엔티티에 넣어준다.
			memberEntity.setMemberBirth(memberDTO.getMemberBirth());
			memberEntity.setMemberEmail(memberDTO.getMemberEmail());
			memberEntity.setMemberMobile(memberDTO.getMemberMobile());
			//memberEntity.setMemberName(memberDTO.getMemberName());		변경대상 아님
			memberEntity.setMemberLicense(memberDTO.getMemberLicense());
			
			//엔티티에는 기존회원번호가 있기때문에 업데이트를 수행한다.그리고 회원번호를 반환한다.
			Long savedId = memberRepository.save(memberEntity).getMemberNo();
			
			return savedId;
	
		}else {	
			return null;
		}

	}
}
