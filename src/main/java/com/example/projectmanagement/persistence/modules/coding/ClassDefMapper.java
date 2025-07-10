package com.example.projectmanagement.persistence.modules.coding;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;

@Mapper
public interface ClassDefMapper {

	public void insertClassDef(ClassDefinitionEntity entity);

	public void updateClassDef(ClassDefinitionEntity entity);
	
	public void deleteClassDef(int id);
	
	public ClassDefinitionEntity findById(int id);
	
	public ClassDefinitionEntity findByTableId(int tableId);
	
}
