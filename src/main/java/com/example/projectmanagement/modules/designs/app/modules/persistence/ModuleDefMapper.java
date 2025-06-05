package com.example.projectmanagement.modules.designs.app.modules.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;

@Mapper
public interface ModuleDefMapper {

	public ModuleDefinitionEntity getModuleById();
	public List<ModuleDefinitionEntity> getAllModules(int projectId);
	public void insertModule(ModuleDefinitionEntity domain);
	public void updateModule(ModuleDefinitionEntity domain);
	public void deleteModule(int targetId);
}
