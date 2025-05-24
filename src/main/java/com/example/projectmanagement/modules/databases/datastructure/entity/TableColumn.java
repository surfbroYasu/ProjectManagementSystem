package com.example.projectmanagement.modules.databases.datastructure.entity;

import lombok.Data;

@Data
public class TableColumn {

	private Integer id;
	private Integer tableInfoId;
	private String columnName;
	private String alias;
	private String dataType;
	private String dataTypeParam;
	private Boolean isPrimary;
	private Boolean isUnique;
	private Boolean isForign;
	private Boolean isNullable;
	private Boolean isAutoIncrement;
	private String defaultValue;
	private Integer forignId;
	private String checkConstraint;
	private String comment;
	private String onDelete;
	private String onUpdate;

	//	結合取得
	private String tableName;
	private String refTableName;
	private String refColumnName;
}
