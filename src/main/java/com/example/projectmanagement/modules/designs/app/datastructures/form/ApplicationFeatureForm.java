package com.example.projectmanagement.modules.designs.app.datastructures.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationFeatureForm {

    private Long id;
    @NotBlank
    private String featureName;
    private String programFeatureName;
    private Integer projectId;
    private Integer historyId;
    
    private Integer moduleId;
    private String description;
    
}
