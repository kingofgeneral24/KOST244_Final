package com.kosta.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.model.Board;
import com.kosta.finalproject.repository.BoardRepository;
@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public Page<Board> boardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
		return boardRepository.findByboardTitleContaining(searchKeyword, pageable);
	}
	
	
	public Page<Board> boardStatusList(int status, Pageable pageable){
		return boardRepository.findByboardStatus(status, pageable);
	}

}