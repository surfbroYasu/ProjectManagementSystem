package com.example.projectmanagement.modules.projects.datastructure.dto;

public record ProjectDevDtoRecord(
		Long id,
		Integer userId,
		Integer teamId,
		Integer projectId,
		String memberName,
		String devRole,
		Boolean isActiveMember,
		String permissionLevel 
		) {}