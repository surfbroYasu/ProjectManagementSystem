package com.example.projectmanagement.modules.coding.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.persistence.modules.coding.ClassDefMapper;

@Service
public class ClassDefRepositoryService {
	
	@Autowired
	private ClassDefMapper mapper;

	public void registerClassDef(ClassDefinitionEntity classDefEntity) {
		mapper.insertClassDef(classDefEntity);
	}
	
	public void updateClassDef(ClassDefinitionEntity classDefEntity) {
		mapper.updateClassDef(classDefEntity);
	}
	
	public void deleteClassDef(int classDefId) {
		mapper.deleteClassDef(classDefId);
	}
	
	
	public ClassDefinitionEntity findClassDefinitionById(int classDefId) {
		return mapper.findById(classDefId);
	}
	
	public ClassDefinitionEntity findClassDefinitionByTableId(int tableId) {
		return mapper.findByTableId(tableId);
	}
}
