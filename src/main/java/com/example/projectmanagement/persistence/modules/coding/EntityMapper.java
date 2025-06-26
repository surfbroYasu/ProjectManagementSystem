package com.example.projectmanagement.persistence.modules.coding;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.Entity;

@Mapper
public interface EntityMapper {

	public void insertEnity(Entity entity);
	public void deleteEntityByTableColId(int tableColId);
	
	public Entity findEntityByTableColId(int tableColumnId);

}
