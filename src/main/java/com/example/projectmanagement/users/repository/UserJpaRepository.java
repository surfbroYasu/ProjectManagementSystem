package com.example.projectmanagement.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.users.datastructure.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer>{
	
	public boolean existsByEmail(String email);

}
