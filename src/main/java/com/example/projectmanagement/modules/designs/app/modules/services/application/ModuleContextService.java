package com.example.projectmanagement.modules.designs.app.modules.services.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;
import com.example.projectmanagement.persistence.modules.designs.app.modules.ModuleDefMapper;

@Service
public class ModuleContextService extends ProjectViewContextService {

	@Autowired
	private ModuleDefMapper mapper;
	
	public List<Integer> makeModuleIdList(List<ModuleDefinitionEntity> modules){
		return modules.stream()
	            .map(ModuleDefinitionEntity::getId)
	            .toList();
	}
	
	public ModuleDefDto setModuleDefDto(ModuleDefinitionEntity entity) {
		return new ModuleDefDto(
				entity.getId(),
				entity.getModuleName(),
				entity.getProgramModuleName(),
				entity.getContext(),
	            entity.getProjectId()
				);
	}
		
	public void setAllModuleEntitiesToModel(Model model, int projectId, String titleMessagePropertyKey) {
	    List<ModuleDefinitionEntity> entities = mapper.getAllModules(projectId);
	    List<ModuleDefDto> dtos = new ArrayList<>();
	    for (ModuleDefinitionEntity e : entities) {
	    	dtos.add(setModuleDefDto(e));
	    }
	    model.addAttribute("moduleList", dtos);
	    	    
	    setProjectToModel(model, projectId);
	    
	    if (!titleMessagePropertyKey.isEmpty()) {
	    	model.addAttribute("title", titleMessagePropertyKey);
	    }
	}
	
}
