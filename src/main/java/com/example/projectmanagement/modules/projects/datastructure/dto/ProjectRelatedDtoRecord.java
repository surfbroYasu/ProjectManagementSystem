package com.example.projectmanagement.modules.projects.datastructure.dto;

import java.time.LocalDate;

/*
 * TIP 使ってないかも
 */
public record ProjectRelatedDtoRecord(
		Integer projectid,
		String projectName,
		String applicationName,
		String sererSideLang,
		Integer clientId,
		LocalDate startDate,
		
		Integer projectDevId,
		Integer userId,
		Integer teamId,
		Integer projectId,
		String memberName,
		String devRole
		) {}