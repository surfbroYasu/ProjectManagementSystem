package com.example.projectmanagement.modules.databases.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableInfoRegisterForm {
	
	private Integer id;
	private Integer dbInfoId;
	
	@NotBlank
	private String tableName;

	private String tableAlias;
	
	private Integer historyId;
	
}
