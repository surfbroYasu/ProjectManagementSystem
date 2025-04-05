package com.example.projectmanagement.modules.databases.domain;

import lombok.Data;

@Data
public class DBInfo {

	private Integer id;
	private Integer projectId;
	private String dbName;
	private String dbms;
	
}
