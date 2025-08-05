package com.example.projectmanagement.modules.designs.app.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.repository.ModuleDefinitionJpaRepository;

@Service("module")
public class ModuleContextService{

	@Autowired
	private FeatureModuleHelper helper;
	
	@Autowired
	private ModuleDefinitionJpaRepository moduleJpa;

		
	public void setAllModuleEntitiesToModel(Model model, Integer projectId) {
	    
		List<ModuleDefinitionEntity> entities = moduleJpa.findAllByProjectId(projectId);
	   
	    List<ModuleDefDto> dtos = helper.getModuleDefDtoList(entities);
	    		
	    model.addAttribute("moduleList", dtos);
	    	    
	}
	
	
}
