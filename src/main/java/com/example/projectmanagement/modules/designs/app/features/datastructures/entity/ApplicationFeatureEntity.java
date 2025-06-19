package com.example.projectmanagement.modules.designs.app.features.datastructures.entity;

import lombok.Data;

@Data
public class ApplicationFeatureEntity {
    private Integer id;
    private String featureName;
    private String programFeatureName;
    private Integer projectId;
    private Integer historyId;
}
