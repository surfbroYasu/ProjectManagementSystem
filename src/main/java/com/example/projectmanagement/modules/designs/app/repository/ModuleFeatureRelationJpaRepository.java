package com.example.projectmanagement.modules.designs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleFeatureRelationEntity;

public interface ModuleFeatureRelationJpaRepository extends JpaRepository<ModuleFeatureRelationEntity, Long> {

	public List<ModuleFeatureRelationEntity> findAllByFeatureIdIn(List<Long> featureIds);
	public List<ModuleFeatureRelationEntity> findAllByFeatureId(Long featureId);
	public List<ModuleFeatureRelationEntity> findAllByModuleId(Integer moduleId);
	public List<ModuleFeatureRelationEntity> findAllByModuleIdIn(List<Integer> moduleIds);
}
