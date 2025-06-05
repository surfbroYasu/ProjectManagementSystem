package com.example.projectmanagement.modules.designs.app.modules.services.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.dto.ModuleDefDto;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.modules.persistence.ModuleDefMapper;
import com.example.projectmanagement.modules.projects.services.ProjectViewContextService;

@Service
public class ModuleContextService extends ProjectViewContextService {

	@Autowired
	private ModuleDefMapper mapper;
	
	public void getAllModuleEntities(Model model, int projectId, String titleMessagePropertyKey) {
	    List<ModuleDefinitionEntity> entities = mapper.getAllModules(projectId);
	    List<ModuleDefDto> dtos = entities.stream()
	        .map(e -> new ModuleDefDto(
	            e.getId(),
	            e.getModuleName(),
	            e.getProgramModuleName(),
	            e.getContext(),
	            projectId))
	        .collect(Collectors.toList());

	    model.addAttribute("moduleList", dtos);
	    	    
	    setProjectToModel(model, projectId);
	    
	    if (!titleMessagePropertyKey.isEmpty()) {
	    	model.addAttribute("title", titleMessagePropertyKey);
	    }
	}
	
}
