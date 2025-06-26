package com.example.projectmanagement.persistence.modules.coding;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassField;

@Mapper
public interface ClassFieldMapper {
	
	public void insertClassField(ClassField entity);

	public void updateClassField(ClassField entity);
	
	public void deleteById(int id);
	
	public ClassField findById(int id);
	
}
