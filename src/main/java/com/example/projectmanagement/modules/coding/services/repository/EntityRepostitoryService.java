package com.example.projectmanagement.modules.coding.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.EntityEntity;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.persistence.modules.coding.EntityMapper;

@Service
public class EntityRepostitoryService {

	@Autowired
	private EntityMapper mapper;

	@Autowired
	private ModelGeneratorFactory modelFactory;
	
	@Autowired
	private ClassDefRepositoryService classDefService;
	
	@Autowired
	private ClassFieldRepostitoryService fieldService;
	
	

	public void registerEntity(EntityEntity entityEntity) {
		mapper.insertEnity(entityEntity);
	}
	
	public void deleteEntityByTableColId(int tableColId) {
		mapper.deleteEntityByTableColId(tableColId);
	}
	
	public EntityEntity findEntityByTableColId(int tableColId) {
		return mapper.findEntityByTableColId(tableColId);
	}
	

}
