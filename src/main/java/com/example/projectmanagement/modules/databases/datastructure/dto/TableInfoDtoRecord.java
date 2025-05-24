package com.example.projectmanagement.modules.databases.datastructure.dto;

public record TableInfoDtoRecord(
		Integer id,
		Integer dbInfoId,
		String tableName,
		String tableAlias
		) {}
