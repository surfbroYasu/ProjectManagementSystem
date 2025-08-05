package com.example.projectmanagement.modules.designs.app.datastructures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "module_definitions")
public class ModuleDefinitionEntity {
	@Id
    private Integer id;
    private String moduleName;
    private String programModuleName;
    private String context;
    private Integer projectId;
}
