package com.example.projectmanagement.modules.projects.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.services.repository.ProjectService;

public abstract class ProjectViewContextService {

	@Autowired
	private ProjectService projectService;

	/**
	 * 
	 *	 [[ モデル一覧 ]] <br/>
	 * 
	 * project： プロジェクト詳細　ProjectDtoRecord
	 * 
	 * @param model
	 * @param projectId
	 */
	public Project setProjectToModel(Model model, Integer projectId) {
		Project entity = projectService.getProjectById(projectId);
		ProjectDtoRecord dto = new ProjectDtoRecord(projectId, entity.getProjectName(), entity.getApplicationName(),
				entity.getServerSideLang(), entity.getClientId(), entity.getStartDate());
		model.addAttribute("project", dto);
		return entity;
	}
	
	/**
	 * 	 [[ モデル一覧 ]]<br/>
	 * 
	 * title： ページタイトル
	 * 
	 * @param model
	 * @param titleProp
	 */
	public void setPageTitle(Model model, String titleProp) {
		model.addAttribute("title", titleProp);
	}

}
