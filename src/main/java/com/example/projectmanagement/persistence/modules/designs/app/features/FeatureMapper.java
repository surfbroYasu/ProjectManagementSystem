package com.example.projectmanagement.persistence.modules.designs.app.features;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;

@Mapper
public interface FeatureMapper {

	public ApplicationFeatureEntity findById();
	public List<ApplicationFeatureEntity> findByIdList(List<Integer> list);
	public void insertApplicationFeature(ApplicationFeatureEntity domain);
	public void updateApplicationFeature(ApplicationFeatureEntity domain);
	public void deleteApplicationFeature(int targetId);
	
	public void insertFeatureModuleRelation(ModuleFeatureRelationEntity rel);
	
	public List<ModuleFeatureRelationEntity> findModuleFeatureByModuleIdList(List<Integer> list);
	public List<ModuleFeatureRelationEntity> findModuleFeatureByFeatureIdList(List<Integer> list);
}
