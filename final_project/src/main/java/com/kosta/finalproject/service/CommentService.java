package com.kosta.finalproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.finalproject.dto.CommentDTO;
import com.kosta.finalproject.entity.CommentEntity;
import com.kosta.finalproject.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	/*댓글 저장*/
	public Long commentSave(CommentDTO commentDTO) {
		
		commentDTO.setCommentRegdt(LocalDateTime.now());
		
		CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO);
		
		Long savedId = commentRepository.save(commentEntity).getCommentNo();
		
		//부모댓글번호가 없으면 parent_no컬럼을 comment_no로 업데이트 한다.
		if(commentDTO.getParentNo() == 0) {
			if(savedId > 0) {
				Optional<CommentEntity> optionalMemberEntity = commentRepository.findById(savedId);
				if(optionalMemberEntity.isPresent()) {
					CommentEntity commentEntity2 = optionalMemberEntity.get();
					//Long타입을 int로 변환
					commentEntity2.setParentNo(Math.toIntExact(savedId));
					savedId = commentRepository.save(commentEntity2).getCommentNo();
				}

			}
		}
		
		return savedId;
		
	}
	
	/*댓글 조회*/
	public List<CommentDTO> commentList(int boardNo) {

		List<CommentEntity> commentEntityList = commentRepository.findByBoardNoOrderByParentNoAscCommentDepthAscCommentRegdtAsc(boardNo);

		List<CommentDTO> CommentDTOList = new ArrayList<>();
		
		for (CommentEntity comment: commentEntityList) {
			CommentDTOList.add(CommentDTO.toCommentDTO(comment));
		}
		
		return CommentDTOList;
		
	}
	
	/*댓글 삭제*/
	public void commentDelete(Long commentNo) {
		commentRepository.deleteById(commentNo);
	}
}
