package com.example.projectmanagement.modules.coding.datastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity {

	private Integer id;
	private Integer tableColumnId;
	private Integer fieldId;
	private Integer projectId;
	
	
	public Entity(Integer tableColumnId, Integer fieldId, Integer projectId) {
		super();
		this.tableColumnId = tableColumnId;
		this.fieldId = fieldId;
		this.projectId = projectId;
	}

}
