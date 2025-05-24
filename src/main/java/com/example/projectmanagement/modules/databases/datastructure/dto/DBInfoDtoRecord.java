package com.example.projectmanagement.modules.databases.datastructure.dto;

public record DBInfoDtoRecord(
	    Integer id,
	    Integer projectId,
	    String dbName,
	    String dbms
	) {}