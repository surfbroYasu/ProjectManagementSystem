package com.example.projectmanagement.modules.coding.services.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.persistence.modules.coding.ClassFieldMapper;

@Service
public class ClassFieldRepostitoryService {
	
	@Autowired
	private ClassFieldMapper mapper;

	public void registerClassField(ClassFieldEntity classFieldEntity) {
		mapper.insertClassField(classFieldEntity);
	}
	
	
	public void updateClassField(ClassFieldEntity entity) {
		mapper.updateClassField(entity);
	}
	
	public void deleteClassFiledById(int classFieldId) {
		mapper.deleteById(classFieldId);
	}

	public ClassFieldEntity findClassFieldById(int classFieldId) {
		return mapper.findById(classFieldId);
	}

	public List<ClassFieldEntity> findnFieldsByClassId(int classDefId) {
		return mapper.findByClassId(classDefId);
	}
	
}
