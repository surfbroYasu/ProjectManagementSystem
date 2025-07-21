package com.example.projectmanagement.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.client.datastructures.entities.ClientUserInfoEntity;

public interface ClientUserJpaRepository extends JpaRepository<ClientUserInfoEntity, Integer> {

	public List<ClientUserInfoEntity> findByClientId(int clientId);

}
