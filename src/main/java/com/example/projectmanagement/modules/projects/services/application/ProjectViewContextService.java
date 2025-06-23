package com.example.projectmanagement.modules.projects.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.services.domain.ProjectService;

public abstract class ProjectViewContextService {

	@Autowired
	private ProjectService projectService;

	public void setProjectToModel(Model model, Integer projectId) {
		Project entity = projectService.getProjectById(projectId);
		ProjectDtoRecord dto = new ProjectDtoRecord(projectId, entity.getProjectName(), entity.getApplicationName(),
				entity.getServerSideLang(), entity.getClientId(), entity.getStartDate());
		model.addAttribute("project", dto);
	}

}
