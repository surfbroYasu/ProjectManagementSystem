package com.example.projectmanagement.modules.databases.domain;

import lombok.Data;

@Data
public class TableInfo {

	private Integer id;
	private Integer dbInfoId;
	private String tableName;
	private String tableAlias;
	private Integer historyId;
	
}
