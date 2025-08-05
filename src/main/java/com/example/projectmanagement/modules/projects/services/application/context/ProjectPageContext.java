package com.example.projectmanagement.modules.projects.services.application.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Service
public class ProjectPageContext{
	
	@Autowired
	@Qualifier("preProject")
	private PreProjectContextService preProjectContext;
	
	@Autowired
	@Qualifier("project")
	private ProjectViewContextService projectContext;
	

	public void setupProjectListPageByUserId(Model model, CustomUserDetails loginUser, String title) {
		preProjectContext.setPageTitle(model, title);
		List<ProjectDtoRecord>projects = projectContext.getProjectDtoListByUserId(loginUser.getUserId());
		preProjectContext.setProjectList(model, projects);
		preProjectContext.setupServerSideLangOpts(model);
	}
	
	public void setupProjectDetailPage(Model model, Integer projectId, CustomUserDetails loginUser, String title) {
		preProjectContext.setPageTitle(model, title);
		projectContext.setProjectToModel(model, projectId);
		projectContext.setPermissionLevel(model, loginUser, projectId);
		projectContext.projectSettingAuth(model, loginUser, projectId);
	}
}
