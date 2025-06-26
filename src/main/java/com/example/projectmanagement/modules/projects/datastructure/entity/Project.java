package com.example.projectmanagement.modules.projects.datastructure.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Project {

	private Integer id;
	private String projectName;
	private String applicationName;
	private String serverSideLang;
	private Integer clientId;
	private LocalDate startDate;
	
}
