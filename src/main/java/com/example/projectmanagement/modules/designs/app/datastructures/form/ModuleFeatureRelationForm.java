package com.example.projectmanagement.modules.designs.app.datastructures.form;

import lombok.Data;

@Data
public class ModuleFeatureRelationForm {

	private Long id;
	private String description;
    private Integer featureId;    
    private Integer moduleId;
    
}
