package com.example.projectmanagement.modules.databases.services.application.validation.columnstructure;

public interface ColumnStructureValidator {

	public ColumnStructureValidationResult validateFkConstraint(boolean isNullable, String fkConstraint, String action);
	public ColumnStructureValidationResult validateDefaultValue(String dataType, String defaultValue);
	
	
}
