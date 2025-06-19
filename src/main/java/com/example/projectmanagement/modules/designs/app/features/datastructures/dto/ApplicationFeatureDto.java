package com.example.projectmanagement.modules.designs.app.features.datastructures.dto;

public record ApplicationFeatureDto(
	    Integer id,
	    String featureName,
	    String programFeatureName,
	    Integer projectId,
	    Integer historyId,
	    String description) {

}
