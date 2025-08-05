package com.example.projectmanagement.modules.projects.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloperEntity;

public interface ProjectDeveloperJpaRepository extends JpaRepository<ProjectDeveloperEntity, Integer> {

	boolean existsByUserId(Integer userId);

	boolean existsByUserIdAndProjectId(Integer userId, Integer projectId);

	Optional<ProjectDeveloperEntity> findByUserIdAndProjectId(Integer userId, Integer projectId);
	
	List<ProjectDeveloperEntity> findAllByUserId(Integer userId);
}
