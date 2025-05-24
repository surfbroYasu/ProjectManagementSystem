package com.example.projectmanagement.modules.projects.datastructure.dto;

import java.time.LocalDate;


public record ProjectDtoRecord(
		Integer id,
		String projectName,
		String applicationName,
		Integer clientId,
		LocalDate startDate) {}
