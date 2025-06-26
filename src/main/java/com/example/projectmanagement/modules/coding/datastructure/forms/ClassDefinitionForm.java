package com.example.projectmanagement.modules.coding.datastructure.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassDefinitionForm {
	private Integer id;
	@NotBlank
    private String className;
    private String classAlias;
    private String description;
    private String dataUseType;
    private Integer historyId;
    private String langage;
    private Integer projectId;
}
