package com.example.projectmanagement.modules.databases.datastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "table_columns")
public class TableColumnEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
