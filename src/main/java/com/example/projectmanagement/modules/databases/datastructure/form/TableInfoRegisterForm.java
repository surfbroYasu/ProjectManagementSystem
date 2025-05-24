package com.example.projectmanagement.modules.databases.datastructure.form;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableInfoRegisterForm {
	
	private Integer id;
	
	@NotNull
	private Integer dbInfoId;
	
	@NotBlank
	private String tableName;

	private String tableAlias;
	
	private Integer historyId;
	
}
