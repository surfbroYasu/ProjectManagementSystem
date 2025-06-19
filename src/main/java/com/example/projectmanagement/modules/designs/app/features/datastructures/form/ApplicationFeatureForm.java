package com.example.projectmanagement.modules.designs.app.features.datastructures.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationFeatureForm {

    private Integer id;
    @NotBlank
    private String featureName;
    private String programFeatureName;
    private Integer projectId;
    private Integer historyId;
    
    private Integer moduleId;
    private String description;
    
}
