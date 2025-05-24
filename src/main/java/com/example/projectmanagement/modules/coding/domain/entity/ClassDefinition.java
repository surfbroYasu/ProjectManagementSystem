package com.example.projectmanagement.modules.coding.domain.entity;

import lombok.Data;

@Data
public class ClassDefinition {
	private Integer id;
    private String className;
    private String classAlias;
    private String description;
    private String dataUseType;
    private Integer historyId;
    private String langage;
    private Integer projectId;
}
