package com.example.projectmanagement.modules.databases.forms;

import java.util.List;

import com.example.projectmanagement.modules.databases.domain.TableColumn;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableColumnRegisterForm {
	
	private Integer id;
	private Integer tableInfoId;
	
	@NotBlank
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
	private String onDelete;
	private String onUpdate;
	
	public List<TableColumn> fkOptions;

}
