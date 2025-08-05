package com.example.projectmanagement.modules.designs.app.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;

@Service
public class ModulePageContext extends ProjectViewContextService {

	@Autowired
	private ModuleContextService context;
	
	public void setupModuleListPage(Model model, Integer projectId, String title) {
		setPageTitle(model, title);
		setProjectToModel(model, projectId);
	
		context.setAllModuleEntitiesToModel(model, projectId);
	}
	
}
