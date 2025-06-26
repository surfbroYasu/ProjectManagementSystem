package com.example.projectmanagement.modules.coding.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.coding.datastructure.entity.Entity;
import com.example.projectmanagement.persistence.modules.coding.EntityMapper;

@Service
public class EntityDomainService {

	@Autowired
	private EntityMapper mapper;

	public void registerEntity(Entity entityEntity) {
		mapper.insertEnity(entityEntity);
	}
	
	public void deleteEntityByTableColId(int tableColId) {
		mapper.deleteEntityByTableColId(tableColId);
	}
	
	public Entity findEntityByTableColId(int tableColId) {
		return mapper.findEntityByTableColId(tableColId);
	}
}
