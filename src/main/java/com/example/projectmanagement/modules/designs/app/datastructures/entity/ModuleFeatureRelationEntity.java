package com.example.projectmanagement.modules.designs.app.datastructures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "module_feature_relations")
public class ModuleFeatureRelationEntity {
	/*
	 * 注意：：Idという名前でコピーされる可能性あり
	 * featureId -> id
	 * moduleId -> id
	 * 
	 * Getterを用意してあるが、運用には注意が必要
	 */
	@Id
    private Long id;
    private Integer moduleId;
    private Long featureId;
    private String description;
    private Integer scheduleId;
}
