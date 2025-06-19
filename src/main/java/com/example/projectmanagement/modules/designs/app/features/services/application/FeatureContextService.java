package com.example.projectmanagement.modules.designs.app.features.services.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.features.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.modules.services.application.ModuleContextService;
import com.example.projectmanagement.modules.projects.services.ProjectViewContextService;
import com.example.projectmanagement.persistence.modules.designs.app.features.FeatureMapper;
import com.example.projectmanagement.persistence.modules.designs.app.modules.ModuleDefMapper;

@Service
public class FeatureContextService extends ProjectViewContextService {

	@Autowired
	private FeatureMapper featureMapper;

	@Autowired
	private ModuleDefMapper moduleMapper;

	@Autowired
	private ModuleContextService moduleService;
	
	@Autowired
	private FeatureContextHelper helper;


	public List<ModuleDefinitionEntity> getAllModules(int projectId) {
		return moduleMapper.getAllModules(projectId);
	}

	public ModuleDefinitionEntity getModuleById(int moduleId) {
		return moduleMapper.getModuleById(moduleId);
	}

	public List<ApplicationFeatureEntity> getFeaturesByIdList(List<Integer> list) {
		return featureMapper.findByIdList(list);
	}
	
	
	/**
	 * モジュールリストList<ModuleDefinitionEntity>から、該当する機能を含めたモジュール機能リストList<ModuleFeaturesDto>を生成し、モデルにセットする。
	 * @param model
	 * @param modules
	 * @param projectId
	 * @param titleMessagePropertyKey
	 */
	public void setFeaturesMapContextFromModules(Model model, List<ModuleDefinitionEntity> modules, Integer projectId,
			String titleMessagePropertyKey) {

		List<ModuleFeaturesDto> moduleFeatures = new ArrayList<>();
		
		if (modules.size() > 0) {

			List<Integer> moduleIds = moduleService.makeModuleIdList(modules);

			List<ModuleFeatureRelationEntity> relations = featureMapper.findModuleFeatureByModuleIdList(moduleIds);
			
			Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = helper.makeModuleIdRelationMap(relations);

			List<ApplicationFeatureEntity> features = featureMapper
					.findByIdList(helper.makeFeatureIdListFromRelation(relations));
			Map<Integer, ApplicationFeatureEntity> featureMap = helper.makeIdFeatureMap(features);

			moduleFeatures = helper.mapModulesFeatures(modules, relationMap, featureMap);

		}
		model.addAttribute("moduleFeaturesList", moduleFeatures);

		if (!titleMessagePropertyKey.isEmpty()) {
			model.addAttribute("title", titleMessagePropertyKey);
		}
		setProjectToModel(model, projectId);
	}

	/**
	 * 機能から、関連するモジュール機能リストList<ModuleFeaturesDto>を生成し、モデルにセットする。
	 * @param model
	 * @param feature
	 * @param projectId
	 * @param titleMessagePropertyKey
	 */
	public void setFeaturesMapContextFromFeatures(Model model, ApplicationFeatureEntity feature, Integer projectId,
			String titleMessagePropertyKey) {

		List<Integer> featureIds = helper.makeFeatureIdListFromFeature(List.of(feature));

		List<ModuleFeatureRelationEntity> relations = featureMapper.findModuleFeatureByFeatureIdList(featureIds);
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = helper.makeModuleIdRelationMap(relations);

		Map<Integer, ApplicationFeatureEntity> featureMap = helper.makeIdFeatureMap(List.of(feature));

		List<ModuleDefinitionEntity> modules = moduleMapper.getModulesByIds(helper.makeModuleIdListFromRelation(relations));

		List<ModuleFeaturesDto> moduleFeatures = helper.mapModulesFeatures(modules, relationMap, featureMap);

		model.addAttribute("moduleFeaturesList", moduleFeatures);
		if (!titleMessagePropertyKey.isEmpty()) {
			model.addAttribute("title", titleMessagePropertyKey);
		}
		setProjectToModel(model, projectId);
	}

}
