package com.example.projectmanagement.modules.projects.services.repository;

import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.projects.constance.ProjectDevRoleEnum;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloperEntity;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;
import com.example.projectmanagement.modules.projects.datastructure.form.ProjectRegisterForm;
import com.example.projectmanagement.modules.projects.repository.ProjectDeveloperJpaRepository;
import com.example.projectmanagement.modules.projects.repository.ProjectJpaRepository;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

import jakarta.transaction.Transactional;

@Service
public class ProjectRepositoryService {


	@Autowired
	private ProjectJpaRepository projectJpaRepo;


	@Transactional
	public void saveByAction(String action, ProjectRegisterForm form, CustomUserDetails loginUser, Locale locale) {
		switch (action) {
			case "add" -> addProject(form, loginUser, locale);
			case "edit" -> editProject(form);
			default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void addProject(ProjectRegisterForm form, CustomUserDetails loginUser, Locale locale) {
		ProjectEntity newProject = createProjectEntity(form);
		ProjectEntity savedProject = projectJpaRepo.save(newProject);
		registerDeveloperToProject(savedProject.getId(), loginUser, locale);
	}

	private void editProject(ProjectRegisterForm form) {
		ProjectEntity project = projectJpaRepo.findById(form.getId())
				.orElseThrow(() -> new IllegalArgumentException("Project not found"));
		BeanUtils.copyProperties(form, project);
		projectJpaRepo.save(project);
	}

	private ProjectEntity createProjectEntity(ProjectRegisterForm form) {
		ProjectEntity entity = new ProjectEntity();
		BeanUtils.copyProperties(form, entity);
		return entity;
	}
	

	@Autowired
	private ProjectDeveloperJpaRepository devJpaRepo;

	private void registerDeveloperToProject(int projectId, CustomUserDetails loginUser, Locale locale) {
		ProjectDeveloperEntity dev = new ProjectDeveloperEntity(	);
		dev.setProjectId(projectId);
		dev.setUserId(loginUser.getUserId());
		dev.setDevRole(ProjectDevRoleEnum.DEV.name());
		dev.setMemberName(loginUser.getFormattedFullName(locale));
		
		devJpaRepo.save(dev);
	}


	public void deleteByIdIfExists(int id) {
		if (projectJpaRepo.existsById(id)) {
			projectJpaRepo.deleteById(id);
		}
	}

	

	public boolean isProjectAccessibleByUser(int projectId, int userId) {
		return devJpaRepo.existsByUserId(userId);
	}
	
	
	public String findServerSideLang(int projectId) {
		ProjectEntity project = projectJpaRepo.findById(projectId).orElseThrow();
		String serverSideLang = project.getServerSideLang();
		return  serverSideLang;
	}
}
