package com.example.projectmanagement.modules.designs.app.service.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.designs.app.datastructures.dto.ApplicationFeatureDto;
import com.example.projectmanagement.modules.designs.app.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleFeatureRelationEntity;

@Service
public class FeatureModuleHelper {

	ApplicationFeatureDto convertToApplicationFeatureDto(ApplicationFeatureEntity entity, String description) {
		return new ApplicationFeatureDto(
				entity.getId(),
				entity.getFeatureName(),
				entity.getProgramFeatureName(),
				entity.getProjectId(),
				entity.getHistoryId(),
				description);
	}

	ModuleFeaturesDto convertToModuleFeatureDto(ModuleDefDto module, List<ApplicationFeatureDto> features) {
		return new ModuleFeaturesDto(
				module,
				features);
	}
	
	public ModuleDefDto convertToModuleDefDto(ModuleDefinitionEntity entity) {
		return new ModuleDefDto(
				entity.getId(),
				entity.getModuleName(),
				entity.getProgramModuleName(),
				entity.getContext(),
	            entity.getProjectId()
				);
	}
	
	public List<ModuleDefDto> getModuleDefDtoList(List<ModuleDefinitionEntity> entities ){
		return entities.stream().map(this :: convertToModuleDefDto).toList();
	}
	
	
	

	List<Long> extractFeatureIdListFromFeature(List<ApplicationFeatureEntity> features) {
		return features.stream().map(ApplicationFeatureEntity::getId).toList();
	}

	List<Long> extractFeatureIdListFromRelation(List<ModuleFeatureRelationEntity> relations) {
		return relations.stream().map(ModuleFeatureRelationEntity::getFeatureId).toList();
	}

	List<Integer> extractModuleIdListFromRelation(List<ModuleFeatureRelationEntity> relations) {
		return relations.stream().map(ModuleFeatureRelationEntity::getModuleId).toList();
	}
	
	List<Integer> extractModuleIdsFromModuleDefList(List<ModuleDefinitionEntity> modules){
		return modules.stream().map(ModuleDefinitionEntity::getId).toList();
	}
	
	
	

	Map<Long, ApplicationFeatureEntity> makeIdFeatureMap(List<ApplicationFeatureEntity> features) {
		return features.stream()
				.collect(Collectors.toMap(ApplicationFeatureEntity::getId, Function.identity()));
	}

	Map<Integer, List<ModuleFeatureRelationEntity>> makeModuleIdRelationMap(List<ModuleFeatureRelationEntity> relations) {
		return relations.stream()
				.collect(Collectors.groupingBy(ModuleFeatureRelationEntity::getModuleId));
	}

	
	List<ModuleFeaturesDto> mapModulesFeaturesDto(List<ModuleDefinitionEntity> modules,
			Map<Integer, List<ModuleFeatureRelationEntity>> relationMap,
			Map<Long, ApplicationFeatureEntity> featureMap) {

		List<ModuleFeaturesDto> moduleFeatures = new ArrayList<>();
		for (ModuleDefinitionEntity module : modules) {
			List<ApplicationFeatureDto> featureDtos = relationMap.getOrDefault(module.getId(), Collections.emptyList())
					.stream()
					.map(rel -> {
						ApplicationFeatureEntity afe = featureMap.get(rel.getFeatureId());
						return afe != null ? convertToApplicationFeatureDto(afe, rel.getDescription()) : null;
					})
					.filter(Objects::nonNull)
					.toList();
			ModuleDefDto moduleDto = convertToModuleDefDto(module);
			moduleFeatures.add(convertToModuleFeatureDto(moduleDto, featureDtos));
		}
		return moduleFeatures;
	}
	
}
