package com.example.projectmanagement.persistence.modules.coding;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.coding.datastructure.entity.EntityEntity;

@Mapper
public interface EntityMapper {

	public void insertEnity(EntityEntity entity);
	public void deleteEntityByTableColId(int tableColId);
	
	public EntityEntity findEntityByTableColId(int tableColumnId);

}
