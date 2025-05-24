package com.example.projectmanagement.modules.projects.datastructure.dto;

public record ProjectDevDtoRecord(
		Integer id,
		Integer userId,
		Integer teamId,
		Integer projectId,
		String memberName,
		String devRole) {}
