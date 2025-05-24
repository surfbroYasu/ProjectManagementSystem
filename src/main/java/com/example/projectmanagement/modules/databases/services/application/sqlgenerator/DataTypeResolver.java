package com.example.projectmanagement.modules.databases.services.application.sqlgenerator;

import java.util.List;

public interface DataTypeResolver {
	List<String> getDataTypeOptions();
	List<String> getFkConstraintOptions();
}
