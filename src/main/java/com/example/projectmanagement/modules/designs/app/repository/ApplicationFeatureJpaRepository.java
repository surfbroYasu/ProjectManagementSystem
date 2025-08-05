package com.example.projectmanagement.modules.designs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.designs.app.datastructures.entity.ApplicationFeatureEntity;

public interface ApplicationFeatureJpaRepository extends JpaRepository<ApplicationFeatureEntity, Long> {
	
	public List<ApplicationFeatureEntity> findAllByIdIn(List<Long> ids);
}
