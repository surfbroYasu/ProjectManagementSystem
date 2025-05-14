package com.example.projectmanagement.modules.databases.validation;

public interface ColumnStructureValidator {

	public ColumnStructureValidationResult validateFkConstraint(boolean isNullable, String fkConstraint, String action);
	public ColumnStructureValidationResult validateDefaultValue(String dataType, String defaultValue);
	
	
}
