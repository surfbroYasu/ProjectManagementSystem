package com.example.projectmanagement.modules.projects.datastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDeveloper {

	private Integer id;
	private Integer userId;
	private Integer teamId;
	private Integer projectId;

	private String memberName;
	private String devRole;
	
}
