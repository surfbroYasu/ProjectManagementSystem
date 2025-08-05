package com.example.projectmanagement.modules.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Integer> {

}
