package com.example.projectmanagement.modules.designs.app.modules.datastructures.dto;

public record ModuleDefDto(
		Integer id,
		String moduleName,
		String programModuleName,
		String context,
		Integer projectId) {
}