package com.example.projectmanagement.modules.projects.datastructure.dto;

import java.time.LocalDate;

/*
 * TIP 使ってない
 */
public record ProjectRelatedDtoRecord(
		Integer projectid,
		String projectName,
		String applicationName,
		String sererSideLang,
		Integer clientId,
		LocalDate startDate,
		
		Long projectDevId,
		Integer userId,
		Integer teamId,
		Integer projectId,
		String memberName,
		String devRole
		) {}