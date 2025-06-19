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

import com.example.projectmanagement.modules.designs.app.features.datastructures.dto.ApplicationFeatureDto;
import com.example.projectmanagement.modules.designs.app.features.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.features.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.modules.services.application.ModuleContextService;

@Service
public class FeatureContextHelper {

	@Autowired
	private ModuleContextService moduleService;

	ApplicationFeatureDto setApplicationFeatureDto(ApplicationFeatureEntity entity, String description) {
		return new ApplicationFeatureDto(
				entity.getId(),
				entity.getFeatureName(),
				entity.getProgramFeatureName(),
				entity.getProjectId(),
				entity.getHistoryId(),
				description);
	}

	ModuleFeaturesDto setModuleFeatureDto(ModuleDefDto module, List<ApplicationFeatureDto> features) {
		return new ModuleFeaturesDto(
				module,
				features);
	}

	List<Integer> makeFeatureIdListFromFeature(List<ApplicationFeatureEntity> features) {
		return features.stream().map(ApplicationFeatureEntity::getId).toList();
	}

	List<Integer> makeFeatureIdListFromRelation(List<ModuleFeatureRelationEntity> relations) {
		return relations.stream().map(ModuleFeatureRelationEntity::getFeatureId).toList();
	}

	List<Integer> makeModuleIdListFromRelation(List<ModuleFeatureRelationEntity> relations) {
		return relations.stream().map(ModuleFeatureRelationEntity::getModuleId).toList();
	}

	Map<Integer, ApplicationFeatureEntity> makeIdFeatureMap(List<ApplicationFeatureEntity> features) {
		return features.stream()
				.collect(Collectors.toMap(ApplicationFeatureEntity::getId, Function.identity()));
	}

	Map<Integer, List<ModuleFeatureRelationEntity>> makeModuleIdRelationMap(
			List<ModuleFeatureRelationEntity> relations) {
		return relations.stream()
				.collect(Collectors.groupingBy(ModuleFeatureRelationEntity::getModuleId));
	}

	List<ModuleFeaturesDto> mapModulesFeatures(List<ModuleDefinitionEntity> modules,
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
}
