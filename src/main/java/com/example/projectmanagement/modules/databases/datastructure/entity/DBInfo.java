package com.example.projectmanagement.modules.databases.datastructure.entity;

import lombok.Data;

@Data
public class DBInfo {

	private Integer id;
	private Integer projectId;
	private String dbName;
	private String dbms;
	
}
