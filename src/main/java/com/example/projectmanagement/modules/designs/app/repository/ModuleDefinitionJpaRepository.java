package com.example.projectmanagement.modules.designs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleDefinitionEntity;

public interface ModuleDefinitionJpaRepository extends JpaRepository<ModuleDefinitionEntity, Integer> {
	
	public List<ModuleDefinitionEntity>findAllByIdIn(List<Integer> ids);
	
	public List<ModuleDefinitionEntity> findAllByProjectId(Integer projectId);
}
