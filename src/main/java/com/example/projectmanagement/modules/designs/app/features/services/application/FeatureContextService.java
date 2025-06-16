package com.example.projectmanagement.modules.designs.app.features.services.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.features.datastructures.dto.ApplicationFeatureDto;
import com.example.projectmanagement.modules.designs.app.features.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.modules.designs.app.features.persistence.FeatureMapper;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.modules.persistence.ModuleDefMapper;
import com.example.projectmanagement.modules.designs.app.modules.services.application.ModuleContextService;
import com.example.projectmanagement.modules.projects.services.ProjectViewContextService;

@Service
public class FeatureContextService extends ProjectViewContextService {

	/*NEED
	 *　１．モジュール一覧事の機能一覧　Map＜Module,　List＜Feature＞＞
	 *	関連テーブルからモジュールIDと機能IDをリスト取得。
	 *モジュールを全件取得
	 *機能を全件取得
	 *ソートしながらMapを作成
	 *
	 *　２．単一モジュールに含まれる機能一覧
	 *　３．機能単体の詳細 	
	 */

	@Autowired
	private FeatureMapper featureMapper;

	@Autowired
	private ModuleDefMapper moduleMapper;

	@Autowired
	private ModuleContextService moduleService;

	private ApplicationFeatureDto setApplicationFeatureDto(ApplicationFeatureEntity entity, String description) {
		return new ApplicationFeatureDto(
				entity.getId(),
				entity.getFeatureName(),
				entity.getProgramFeatureName(),
				entity.getProjectId(),
				entity.getHistoryId(),
				description);
	}

	private ModuleFeaturesDto setModuleFeatureDto(ModuleDefDto module, List<ApplicationFeatureDto> features) {
		return new ModuleFeaturesDto(
				module,
				features);
	}
	

	private List<Integer> makeFeatureIdListFromFeature(List<ApplicationFeatureEntity> features){
		return features.stream().map(ApplicationFeatureEntity::getId).toList();
	}
	
	private List<Integer> makeFeatureIdListFromRelation(List<ModuleFeatureRelationEntity> relations){
		return relations.stream().map(ModuleFeatureRelationEntity::getFeatureId).toList();
	}
	
	private List<Integer> makeModuleIdListFromRelation(List<ModuleFeatureRelationEntity> relations){
		return relations.stream().map(ModuleFeatureRelationEntity::getModuleId).toList();
	}
	
	
	private Map<Integer, ApplicationFeatureEntity> makeIdFeatureMap(List<ApplicationFeatureEntity> features) {
		return features.stream()
				.collect(Collectors.toMap(ApplicationFeatureEntity::getId, Function.identity()));
	}

	private Map<Integer, List<ModuleFeatureRelationEntity>> makeModuleIdRelationMap(
			List<ModuleFeatureRelationEntity> relations) {
		return relations.stream()
				.collect(Collectors.groupingBy(ModuleFeatureRelationEntity::getModuleId));
	}
	

	private List<ModuleFeaturesDto> mapModulesFeatures(List<ModuleDefinitionEntity> modules,
			Map<Integer, List<ModuleFeatureRelationEntity>> relationMap,
			Map<Integer, ApplicationFeatureEntity> featureMap) {

		List<ModuleFeaturesDto> moduleFeatures = new ArrayList<>();
		for (ModuleDefinitionEntity module : modules) {
			List<ApplicationFeatureDto> featureDtos = relationMap.getOrDefault(module.getId(), Collections.emptyList())
					.stream()
					.map(rel -> {
						ApplicationFeatureEntity afe = featureMap.get(rel.getFeatureId());
						return afe != null ? setApplicationFeatureDto(afe, rel.getDescription()) : null;
					})
					.filter(Objects::nonNull)
					.toList();
			ModuleDefDto moduleDto = moduleService.setModuleDefDto(module);
			moduleFeatures.add(setModuleFeatureDto(moduleDto, featureDtos));
		}
		return moduleFeatures;
	}

	
	public List<ModuleDefinitionEntity> getAllModules(int projectId) {
		return moduleMapper.getAllModules(projectId);
	}

	public List<ModuleDefinitionEntity> getModuleById(int moduleId) {
		return List.of(moduleMapper.getModuleById());
	}
	
	public List<ApplicationFeatureEntity> getFeaturesByIdList(List<Integer> list){
		return featureMapper.findByIdList(list);
	}

	public void setFeaturesMapContextFromModules(Model model, List<ModuleDefinitionEntity> modules, Integer projectId,
			String titleMessagePropertyKey) {

		List<Integer> moduleIds = moduleService.makeModuleIdList(modules);

		List<ModuleFeatureRelationEntity> relations = featureMapper.findModuleFeatureByModuleIdList(moduleIds);
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = makeModuleIdRelationMap(relations);

		List<ApplicationFeatureEntity> features = featureMapper.findByIdList(makeFeatureIdListFromRelation(relations));
		Map<Integer, ApplicationFeatureEntity> featureMap = makeIdFeatureMap(features);

		List<ModuleFeaturesDto> moduleFeatures = mapModulesFeatures(modules, relationMap, featureMap);

		model.addAttribute("moduleFeaturesList", moduleFeatures);
		if (!titleMessagePropertyKey.isEmpty()) {
			model.addAttribute("title", titleMessagePropertyKey);
		}
		setProjectToModel(model, projectId);
	}
	
	public void setFeaturesMapContextFromFeatures(Model model, ApplicationFeatureEntity feature, Integer projectId,
			String titleMessagePropertyKey) {
		
		List<Integer> featureIds = makeFeatureIdListFromFeature(List.of(feature));
		
		List<ModuleFeatureRelationEntity> relations = featureMapper.findModuleFeatureByFeatureIdList(featureIds);
		Map<Integer, List<ModuleFeatureRelationEntity>> relationMap = makeModuleIdRelationMap(relations);
		
		Map<Integer, ApplicationFeatureEntity> featureMap = makeIdFeatureMap(List.of(feature));
		
		List<ModuleDefinitionEntity> modules = moduleMapper.getModulesByIds(makeModuleIdListFromRelation(relations));
				
		
		List<ModuleFeaturesDto> moduleFeatures = mapModulesFeatures(modules, relationMap, featureMap);
		
		model.addAttribute("moduleFeaturesList", moduleFeatures);
		if (!titleMessagePropertyKey.isEmpty()) {
			model.addAttribute("title", titleMessagePropertyKey);
		}
		setProjectToModel(model, projectId);
	}

}
