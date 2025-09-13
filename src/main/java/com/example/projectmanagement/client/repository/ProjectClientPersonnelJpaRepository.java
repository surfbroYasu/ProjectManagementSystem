package com.example.projectmanagement.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.client.datastructures.entities.ProjectClientPersonnelEntity;

public interface ProjectClientPersonnelJpaRepository extends JpaRepository<ProjectClientPersonnelEntity, Integer> {

	public List<ProjectClientPersonnelEntity> findByProjectClient_Client_Id(int clientId);

}
