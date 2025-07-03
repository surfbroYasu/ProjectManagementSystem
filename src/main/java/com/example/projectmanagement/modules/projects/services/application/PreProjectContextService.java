package com.example.projectmanagement.modules.projects.services.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.generalutil.enums.SupportedProgramingLangage;
import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.services.repository.ProjectService;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Service
public class PreProjectContextService {

	@Autowired
	private ProjectService service;

	public void setPerProjectContext(Model model, CustomUserDetails loginUser, String title) {
		model.addAttribute("title", "title.project.top");
		List<Project>projectEntities = service.getProjects(loginUser.getUserId());
		
		model.addAttribute("projects", convertProjectEntityToDto(projectEntities));
		model.addAttribute("serverSideLangs", SupportedProgramingLangage.getStringLanguageCodes());
	}
	
	private List<ProjectDtoRecord> convertProjectEntityToDto(List<Project> entities) {
		return entities.stream()
			.map(this::projectEntityToDto)
			.collect(Collectors.toList());
	}

	
	private ProjectDtoRecord projectEntityToDto(Project entity) {
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
