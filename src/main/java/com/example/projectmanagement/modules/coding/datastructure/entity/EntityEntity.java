package com.example.projectmanagement.modules.coding.datastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityEntity {

	private Integer id;
	private Long tableColumnId;
	private Long fieldId;
	private Integer projectId;
	
	
	public EntityEntity(Long tableColumnId, Long fieldId, Integer projectId) {
		super();
		this.tableColumnId = tableColumnId;
		this.fieldId = fieldId;
		this.projectId = projectId;
	}

}
