package com.example.projectmanagement.modules.projects.datastructure.dto;

import java.time.LocalDate;


public record ProjectDtoRecord(
		Integer id,
		String projectName,
		String applicationName,
		String serverSideLang,
		Integer clientId,
		LocalDate startDate) {}
