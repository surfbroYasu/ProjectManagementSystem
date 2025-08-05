package com.example.projectmanagement.modules.databases.datastructure.dto;

public record ColumnDtoRecord(

		Long id,
		Long tableInfoId,
		String columnName,
		String alias,
		String dataType,
		String dataTypeParam,
		Boolean isPrimary,
		Boolean isUnique,
		Boolean isForign,
		Boolean isNullable,
		Boolean isAutoIncrement,
		String defaultValue,
		Integer forignId,
		String checkConstraint,
		String comment,
		String onDelete,
		String onUpdate) {

}
