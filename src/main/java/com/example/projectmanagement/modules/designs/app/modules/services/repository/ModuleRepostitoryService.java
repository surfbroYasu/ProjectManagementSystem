package com.example.projectmanagement.modules.designs.app.modules.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.persistence.modules.designs.app.modules.ModuleDefMapper;

@Service
public class ModuleRepostitoryService {

	@Autowired
	private ModuleDefMapper mapper;
	
	public void createModule(ModuleDefinitionEntity module) {
		mapper.insertModule(module);
	}
	
	public void updateModule(ModuleDefinitionEntity module) {
		mapper.updateModule(module);
	}
	
	public void deleteModule(int targetId) {
		mapper.deleteModule(targetId);
	}
}
