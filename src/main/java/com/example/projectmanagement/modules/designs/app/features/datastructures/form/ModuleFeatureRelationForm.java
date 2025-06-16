package com.example.projectmanagement.modules.designs.app.features.datastructures.form;

import lombok.Data;

@Data
public class ModuleFeatureRelationForm {
	
    private Integer featureId;    
    private Integer moduleId;
    private String description;
    
}
