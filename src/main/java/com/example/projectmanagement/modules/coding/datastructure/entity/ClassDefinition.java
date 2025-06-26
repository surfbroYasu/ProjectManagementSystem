package com.example.projectmanagement.modules.coding.datastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDefinition {
	private Integer id;
    private String className;
    private String classAlias;
    private String description;
    private String dataUseType;
    private Integer historyId;
    private String language;
    private Integer projectId;
    private Integer tableId;
    private String structualType;
    
	public ClassDefinition(String className, String classAlias, String description, String dataUseType, String language,
			Integer projectId) {
		super();
		this.className = className;
		this.classAlias = classAlias;
		this.description = description;
		this.dataUseType = dataUseType;
		this.language = language;
		this.projectId = projectId;
	}
    
    
}
