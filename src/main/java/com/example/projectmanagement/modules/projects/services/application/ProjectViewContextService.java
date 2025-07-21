package com.example.projectmanagement.modules.projects.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;
import com.example.projectmanagement.modules.projects.repository.ProjectJpaRepository;

public abstract class ProjectViewContextService {

//	@Autowired
//	private ProjectRepositoryService projectService;
	
	@Autowired
	private ProjectJpaRepository projectJpaRepo;

	/**
	 * 
	 *	 [[ モデル一覧 ]] <br/>
	 * 
	 * project： プロジェクト詳細　ProjectDtoRecord
	 * 
	 * @param model
	 * @param projectId
	 */
	public ProjectEntity setProjectToModel(Model model, Integer projectId) {
		return projectJpaRepo.findById(projectId)
			.map(entity -> {
				model.addAttribute("project", convertToDto(entity));
				return entity;
			})
			.orElseGet(() -> {
				model.addAttribute("projectNotFound", true);
				return null;
			});
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
	
	
	private ProjectDtoRecord convertToDto(ProjectEntity entity) {
		return new ProjectDtoRecord(
				entity.getId(),
				entity.getProjectName(),
				entity.getApplicationName(),
				entity.getServerSideLang(),
				entity.getClientId(),
				entity.getStartDate()
				);
	}

}
