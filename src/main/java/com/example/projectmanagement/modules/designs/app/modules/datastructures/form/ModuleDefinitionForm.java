package com.example.projectmanagement.modules.designs.app.modules.datastructures.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModuleDefinitionForm {
    private Integer id;
    @NotBlank
    private String moduleName;
    private String programModuleName;
    private String context;
    private Integer projectId;
}
