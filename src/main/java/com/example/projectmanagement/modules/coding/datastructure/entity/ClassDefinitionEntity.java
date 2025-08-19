package com.example.projectmanagement.modules.coding.datastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDefinitionEntity {
	private Integer id;
    private String className;
    private String classAlias;
    private String description;
    private String classType;
    private Integer historyId;
    private String language;
    private Integer projectId;
    private Long tableId;
    private String structualType;
    
	public ClassDefinitionEntity(String className, String classAlias, String description, String classType, String language,
			Integer projectId) {
		super();
		this.className = className;
		this.classAlias = classAlias;
		this.description = description;
		this.classType = classType;
		this.language = language;
		this.projectId = projectId;
	}
    
    
}
