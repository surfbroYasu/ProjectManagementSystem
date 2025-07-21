package com.example.projectmanagement.modules.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloperEntity;

public interface ProjectDeveloperJpaRepository extends JpaRepository<ProjectDeveloperEntity, Integer> {

	public boolean existsByUserId(Integer userId);
}
