package com.example.projectmanagement.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.client.datastructures.entities.ClientBasicInfoEntity;

public interface ClientInfoJpaRepository extends JpaRepository<ClientBasicInfoEntity, Integer> {
	
	public List<ClientBasicInfoEntity> findAllByPhone(String phone);

}
