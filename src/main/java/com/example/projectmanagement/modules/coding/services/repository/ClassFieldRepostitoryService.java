package com.example.projectmanagement.modules.coding.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassField;
import com.example.projectmanagement.persistence.modules.coding.ClassFieldMapper;

@Service
public class ClassFieldRepostitoryService {
	
	@Autowired
	private ClassFieldMapper mapper;

	public void registerClassField(ClassField classFieldEntity) {
		mapper.insertClassField(classFieldEntity);
	}
	
	
	public void updateClassField(ClassField entity) {
		mapper.updateClassField(entity);
	}
	
	public void deleteClassFiledById(int classFieldId) {
		mapper.deleteById(classFieldId);
	}

	public ClassField findClassFieldById(int classFieldId) {
		return mapper.findById(classFieldId);
	}
	
}
