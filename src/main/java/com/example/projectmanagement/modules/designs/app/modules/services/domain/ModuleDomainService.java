package com.example.projectmanagement.modules.designs.app.modules.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.persistence.modules.designs.app.modules.ModuleDefMapper;

@Service
public class ModuleDomainService {

	@Autowired
	private ModuleDefMapper mapper;
	
	public void createModule(ModuleDefinitionEntity domain) {
		mapper.insertModule(domain);
	}
	
	public void updateModule(ModuleDefinitionEntity domain) {
		mapper.updateModule(domain);
	}
	
	public void deleteModule(int targetId) {
		mapper.deleteModule(targetId);
	}
}
