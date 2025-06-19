package com.example.projectmanagement.modules.designs.app.features.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.persistence.modules.designs.app.features.FeatureMapper;

@Service
public class FeatureDomainService {

	@Autowired
	private FeatureMapper mapper;
	
	
	public void createFeature(ApplicationFeatureEntity domain) {
		mapper.insertApplicationFeature(domain);
	}
	
	public void updateFeature(ApplicationFeatureEntity domain) {
		mapper.updateApplicationFeature(domain);
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
