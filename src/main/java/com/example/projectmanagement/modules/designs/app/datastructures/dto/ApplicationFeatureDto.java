package com.example.projectmanagement.modules.designs.app.datastructures.dto;

public record ApplicationFeatureDto(
	    Long id,
	    String featureName,
	    String programFeatureName,
	    Integer projectId,
	    Integer historyId,
	    String description) {

}
