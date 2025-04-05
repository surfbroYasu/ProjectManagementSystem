package com.example.projectmanagement.modules.databases.domain;

import lombok.Data;

@Data
public class TableColumn {

	private Integer id;
	private Integer tableInfoId;
	private String columnName;
	private String alias;
	private String dataType;
	private String dataTypeParam;
	private Boolean isPk;
	private Boolean isUnique;
	private Boolean isFK;
	private Boolean isNullable;
	private Boolean isAutoIncrement;
	private String defaultValue;
	private Integer fkId;
	private String checkConstraint;
	private String comment;
	
}
