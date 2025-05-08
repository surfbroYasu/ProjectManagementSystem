package com.example.projectmanagement.persistence.modules.projects;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.projects.domain.Project;

@Mapper
public interface ProjectMapper {
	
	public Project getProjectById(int projectId);

	public List<Project> getAllProjectsByUser(int userId);
	
	public void insertProject(Project project);
	
	public boolean existsUserProject(int userId, int projectId);
}
