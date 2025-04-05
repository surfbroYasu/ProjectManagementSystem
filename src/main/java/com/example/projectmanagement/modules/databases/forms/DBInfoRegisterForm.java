package com.example.projectmanagement.modules.databases.forms;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DBInfoRegisterForm {
	
	private Integer id;
	
	@NotNull
	private Integer projectId;
	
	@NotBlank
	private String dbName;
	
	@NotBlank
	private String dbms;
	

}
