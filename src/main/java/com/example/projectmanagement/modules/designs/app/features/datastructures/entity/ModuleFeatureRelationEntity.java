package com.example.projectmanagement.modules.designs.app.features.datastructures.entity;

import lombok.Data;

@Data
public class ModuleFeatureRelationEntity {
	/*
	 * 注意：：Idという名前でコピーされる可能性あり
	 * featureId -> id
	 * moduleId -> id
	 * 
	 * Getterを用意してあるが、運用には注意が必要
	 */
	
    private Integer id;
    private Integer moduleId;
    private Integer featureId;
    private String description;
    private Integer scheduleId;
}
