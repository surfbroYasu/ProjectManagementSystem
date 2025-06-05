package com.example.projectmanagement.modules.designs.app.features.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;

@Mapper
public interface FeatureMapper {

	public ApplicationFeatureEntity findById();
	public List<ApplicationFeatureEntity> findByIdList();
	public void insertApplicationFeature(ApplicationFeatureEntity domain);
	public void updateApplicationFeature(ApplicationFeatureEntity domain);
	
}
