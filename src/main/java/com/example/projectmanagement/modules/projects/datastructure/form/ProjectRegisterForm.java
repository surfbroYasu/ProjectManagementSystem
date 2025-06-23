package com.example.projectmanagement.modules.projects.datastructure.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRegisterForm {
	
	private Integer id;

	@NotBlank
	private String projectName;

	private String applicationName;

	private String serverSideLang;
	
	private Integer clientId;
	
	public LocalDate getStartDate() {
		return LocalDate.now();
	}
	
}
