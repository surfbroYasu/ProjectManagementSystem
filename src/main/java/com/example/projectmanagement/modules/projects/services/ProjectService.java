package com.example.projectmanagement.modules.projects.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.projects.domain.Project;
import com.example.projectmanagement.persistence.modules.projects.ProjectMapper;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectMapper mapper;
	
	public void insertProject(Project project) {
		mapper.insertProject(project);
	}
	
	public List<Project> getProjects(int userId){
		return mapper.getAllProjectsByUser(userId);
	}
	
	public Project getProjectById(int projectId){
		return mapper.getProjectById(projectId);
	}
	
	public boolean isProjectAccessibleByUser(int projectId, int userId) {
	    return mapper.existsUserProject(userId, projectId);
	}


	
}
