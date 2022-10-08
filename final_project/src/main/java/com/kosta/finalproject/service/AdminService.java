package com.kosta.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kosta.finalproject.dto.AdminDTO;
import com.kosta.finalproject.entity.AdminEntity;
import com.kosta.finalproject.repository.AdminRepository;
import com.kosta.finalproject.repository.BoardRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminService {
	private AdminRepository adminRepository;
	
	@Transactional 
	public Long savePost(AdminDTO adminDTO) {
		return BoardRepository.save(adminDTO.toEntity()).getId();
	}
	
	public List<AdminDTO> getAdminlist() {
		List<AdminEntity>adminEntities = adminRepository.findAll();
		List<AdminDTO>adminDTO = new ArrayList<>();
		
		for(AdminEntity adminEntity : adminEntities) {
			AdminDTO boardDTO = AdminDTO.builder()	
							 .id(adminEntity.getId())
							 .title(adminEntity.getTitle())
							 .content(adminEntity.getContent())
							 .writer(adminEntity.getWriter())
							 .createdDate(adminEntity.getCreatedDate())
							 .build();
			adminDTOList.add(boardDTO);
		}
		return adminDTOList;
	}

}
