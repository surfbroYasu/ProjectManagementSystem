package com.example.projectmanagement.modules.designs.app.features.datastructures.entity;

import lombok.Data;

@Data
public class ModuleFeatureRelationEntity {
    private Integer id;
    private Integer moduleId;
    private Integer featureId;
    private String description;
    private Integer scheduleId;
}
