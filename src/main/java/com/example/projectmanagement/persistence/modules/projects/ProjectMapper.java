package com.example.projectmanagement.persistence.modules.projects;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.projects.domain.Project;

@Mapper
public interface ProjectMapper {
	
	public List<Project> getAllProjects(int organizationId);

}
