package com.example.projectmanagement.persistence.modules.coding;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;

@Mapper
public interface ClassFieldMapper {
	
	public void insertClassField(ClassFieldEntity entity);

	public void updateClassField(ClassFieldEntity entity);
	
	public void deleteById(int id);
	
	public ClassFieldEntity findById(int id);
	
	public List<ClassFieldEntity> findByClassId(int classId);
	
}
