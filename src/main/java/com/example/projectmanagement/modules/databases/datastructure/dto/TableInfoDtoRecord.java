package com.example.projectmanagement.modules.databases.datastructure.dto;

public record TableInfoDtoRecord(
		Long id,
		Integer dbInfoId,
		String tableName,
		String tableAlias
		) {}
