package com.example.projectmanagement.modules.designs.app.datastructures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "application_features")
public class ApplicationFeatureEntity {
	@Id
    private Long id;
    private String featureName;
    private String programFeatureName;
    private Integer projectId;
    private Integer historyId;
}
