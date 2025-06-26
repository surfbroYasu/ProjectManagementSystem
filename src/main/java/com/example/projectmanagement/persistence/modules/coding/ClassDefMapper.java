package com.example.projectmanagement.persistence.modules.coding;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;

@Mapper
public interface ClassDefMapper {

	public void insertClassDef(ClassDefinition entity);

	public void updateClassDef(ClassDefinition entity);
	
	public void deleteClassDef(int id);
	
	public ClassDefinition findById(int id);
	
	public ClassDefinition findByTableId(int tableId);
	
}
