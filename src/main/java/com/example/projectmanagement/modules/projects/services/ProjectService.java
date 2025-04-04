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
	
	public List<Project> getProjects(){
		return mapper.getAllProjects();
	}

	
}
