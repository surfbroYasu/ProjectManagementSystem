package com.example.projectmanagement.modules.designs.app.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.datastructures.dto.ModuleFeaturesDto;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.repository.ApplicationFeatureJpaRepository;
import com.example.projectmanagement.modules.designs.app.repository.ModuleDefinitionJpaRepository;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;

@Service
public class FeaturePageContext extends ProjectViewContextService {

	@Autowired
	private ApplicationFeatureJpaRepository featureJpa;
	
	@Autowired
	private ModuleDefinitionJpaRepository moduleJpa;


	@Autowired
	private ModuleFeatureMultiService multiService;

	public void setupFeatureListPage(Model model, Integer projectId, String title) {

		setProjectToModel(model, projectId);
		setPageTitle(model, title);

		List<ModuleFeaturesDto> moduleFeatures = multiService.getModuleFeaturtesDtoByProjectId(projectId);
		multiService.setupFeaturesMapContext(model, moduleFeatures);

	}

	public void setupRelatedFeaturesOfModulePage(Model model, Integer projectId, Integer moduleId, String title) {

		setProjectToModel(model, projectId);
		setPageTitle(model, title);
		
		ModuleDefinitionEntity module =moduleJpa.findById(moduleId).orElseThrow();

		List<ModuleFeaturesDto> moduleFeatures = multiService.getModuleFeaturtesDtoByModule(module);
		multiService.setupFeaturesMapContext(model, moduleFeatures);
	}
	
	public void setupRelatedModuleFeaturePage(Model model, Integer projectId, Long featureId, String title) {
		
		setProjectToModel(model, projectId);
		setPageTitle(model, title);
		
		ApplicationFeatureEntity feature =	featureJpa.findById(featureId).orElseThrow();
		
		List<ModuleFeaturesDto> moduleFeatures = multiService.getModuleFeaturtesDtoByFeature(feature);
		multiService.setupFeaturesMapContext(model, moduleFeatures);
	}

}
