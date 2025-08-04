package com.example.projectmanagement.modules.projects.datastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_developers")
public class ProjectDeveloperEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer userId;
	private Integer teamId;
	private Integer projectId;

	private String memberName;
	private String devRole;
	
	private Boolean isActiveMember;
	private String permissionLevel;


}
