package com.example.projectmanagement.modules.designs.app.modules.datastructures.entity;

import lombok.Data;

@Data
public class ModuleDefinitionEntity {
    private Integer id;
    private String moduleName;
    private String programModuleName;
    private String context;
    private Integer projectId;
}
