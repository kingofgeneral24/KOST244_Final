package com.kosta.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long>{
}
