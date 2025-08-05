package com.example.projectmanagement.persistence.modules.projects;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;

@Mapper
public interface ProjectMapper {
	
	public List<ProjectEntity> getAllProjectsByUser(int userId);
	
}
