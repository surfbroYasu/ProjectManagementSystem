package com.example.projectmanagement.modules.projects.domain;

import lombok.Data;

@Data
public class Project {

	private Integer id;
	private String projectName;
	private Integer clientId;
	private String startDate;
	private Integer ownedBy;
	private String visibility;
	
}
