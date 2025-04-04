package com.example.projectmanagement.modules.projects.forms;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRegisterForm {

	@NotBlank
	private String projectName;

	private String applicationName;
	
	private Integer clientId;
	
	public LocalDate getStartDate() {
		return LocalDate.now();
	}
	
}
