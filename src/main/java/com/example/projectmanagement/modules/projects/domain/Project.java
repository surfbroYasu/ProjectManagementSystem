package com.example.projectmanagement.modules.projects.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Project {

	private Integer id;
	private String projectName;
	private String applicationName;
	private Integer clientId;
	private LocalDate startDate;
	
}
