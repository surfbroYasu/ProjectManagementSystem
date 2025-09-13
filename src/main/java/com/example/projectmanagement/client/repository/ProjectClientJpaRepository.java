package com.example.projectmanagement.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.client.datastructures.entities.ProjectClientEntity;

public interface ProjectClientJpaRepository extends JpaRepository<ProjectClientEntity, Integer> {
	
	public Optional<ProjectClientEntity> findByProjectId(int projectId);

}
