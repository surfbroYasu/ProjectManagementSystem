package com.example.projectmanagement.modules.designs.app.service.application;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.modules.designs.app.repository.ApplicationFeatureJpaRepository;
import com.example.projectmanagement.modules.designs.app.repository.ModuleDefinitionJpaRepository;
import com.example.projectmanagement.modules.designs.app.repository.ModuleFeatureRelationJpaRepository;

@Service
public class ModuleFeatureMultiService {
	
	@Autowired
	private FeatureModuleHelper helper;
	
	@Autowired
	private ApplicationFeatureJpaRepository featureJpa;

	@Autowired
	private ModuleFeatureRelationJpaRepository relationJpa;
	
	@Autowired
	private ModuleDefinitionJpaRepository moduleJpa;
	
	
	public List<ModuleFeaturesDto> getModuleFeaturtesDtoByProjectId(Integer projectId){
		
		List<ModuleDefinitionEntity> modules = moduleJpa.findAllByProjectId(projectId);
		
		List<ModuleFeatureRelationEntity> relations = relationJpa.findAllByModuleIdIn(helper.extractModuleIdsFromModuleDefList(modules));
		
		List<ApplicationFeatureEntity> features = featureJpa.findAllByIdIn(helper.extractFeatureIdListFromRelation(relations));
		
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = helper.makeModuleIdRelationMap(relations);
		Map<Long, ApplicationFeatureEntity>featureMap = helper.makeIdFeatureMap(features);
		
		List<ModuleFeaturesDto> moduleFeatures = helper.mapModulesFeaturesDto(modules, relationMap, featureMap);
		
		return moduleFeatures;
	}
	
	
	public List<ModuleFeaturesDto> getModuleFeaturtesDtoByModule(ModuleDefinitionEntity module){

		List<ModuleFeatureRelationEntity> relations = relationJpa.findAllByModuleId(module.getId());
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = helper.makeModuleIdRelationMap(relations);

		List<ApplicationFeatureEntity> features = featureJpa.findAllByIdIn(helper.extractFeatureIdListFromRelation(relations));
		Map<Long, ApplicationFeatureEntity> featureMap = helper.makeIdFeatureMap(features);

		List<ModuleFeaturesDto> moduleFeatures = helper.mapModulesFeaturesDto(List.of(module), relationMap, featureMap);
		
		return moduleFeatures;
	}
	
	
	public List<ModuleFeaturesDto> getModuleFeaturtesDtoByFeature(ApplicationFeatureEntity feature){
		
		List<ModuleFeatureRelationEntity> relations = relationJpa.findAllByFeatureId(feature.getId());
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = helper.makeModuleIdRelationMap(relations);
		
		Map<Long, ApplicationFeatureEntity> featureMap = helper.makeIdFeatureMap(List.of(feature));
		
		List<ModuleDefinitionEntity> modules = moduleJpa.findAllByIdIn(helper.extractModuleIdListFromRelation(relations));
		
		List<ModuleFeaturesDto> moduleFeatures = helper.mapModulesFeaturesDto(modules, relationMap, featureMap);
		
		return moduleFeatures;
	}
	
	
	
	
	
	public void setupFeaturesMapContext(Model model, List<ModuleFeaturesDto> moduleFeatures) {
		model.addAttribute("moduleFeaturesList", moduleFeatures);
	}
	
}
