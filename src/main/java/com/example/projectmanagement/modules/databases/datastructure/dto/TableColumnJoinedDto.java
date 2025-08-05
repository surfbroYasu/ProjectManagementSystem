package com.example.projectmanagement.modules.databases.datastructure.dto;

import lombok.Data;

@Data
public class TableColumnJoinedDto {
	private Long id;
	private Long tableInfoId;
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
