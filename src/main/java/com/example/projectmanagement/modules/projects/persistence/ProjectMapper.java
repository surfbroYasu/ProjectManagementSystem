package com.example.projectmanagement.modules.projects.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloper;

@Mapper
public interface ProjectMapper {
	
	public Project getProjectById(int projectId);

	public List<Project> getAllProjectsByUser(int userId);
	
	public boolean existsUserProject(int userId, int projectId);
	
	public void insertProject(Project project);
	
	public void insertProjectDev(ProjectDeveloper devInfo);

	public void updateProject(Project project);
	
	public void deleteProject(int projectId);
}
