package com.example.projectmanagement.modules.designs.app.features.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.persistence.modules.designs.app.features.FeatureMapper;

@Service
public class FeatureRepositoryService {

	@Autowired
	private FeatureMapper mapper;
	
	
	public void createFeature(ApplicationFeatureEntity appFeature) {
		mapper.insertApplicationFeature(appFeature);
	}
	
	public void updateFeature(ApplicationFeatureEntity appFeature) {
		mapper.updateApplicationFeature(appFeature);
	}
	
	public void deleteFeature(int targetId) {
		mapper.deleteApplicationFeature(targetId);
	}
	
	
	public void createFeatureNModuleRelation(ApplicationFeatureEntity feature, ModuleFeatureRelationEntity rel) {
		createFeature(feature);

		rel.setFeatureId(feature.getId());

		mapper.insertFeatureModuleRelation(rel);
	}
	
	
}
