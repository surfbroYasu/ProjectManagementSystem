package com.example.projectmanagement.modules.coding.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;
import com.example.projectmanagement.persistence.modules.coding.ClassDefMapper;

@Service
public class ClassDefRepositoryService {
	
	@Autowired
	private ClassDefMapper mapper;

	public void registerClassDef(ClassDefinition classDefEntity) {
		mapper.insertClassDef(classDefEntity);
	}
	
	public void updateClassDef(ClassDefinition classDefEntity) {
		mapper.updateClassDef(classDefEntity);
	}
	
	public void deleteClassDef(int classDefId) {
		mapper.deleteClassDef(classDefId);
	}
	
	
	public ClassDefinition findClassDefinitionById(int classDefId) {
		return mapper.findById(classDefId);
	}
	
	public ClassDefinition findClassDefinitionByTableId(int tableId) {
		return mapper.findByTableId(tableId);
	}
}
