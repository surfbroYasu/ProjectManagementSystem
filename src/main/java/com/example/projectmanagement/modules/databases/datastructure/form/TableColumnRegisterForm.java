package com.example.projectmanagement.modules.databases.datastructure.form;

import java.util.List;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableColumnRegisterForm {
	
	private Long id;
	private Long tableInfoId;
	
	@NotBlank
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
	
	public List<TableColumnEntity> forignOptions;

	
}
