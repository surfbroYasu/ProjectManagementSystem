package com.example.projectmanagement.modules.databases.services.application.sqlgenerator.mariadb;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;

@Component("mariadbType")
public class MariaDbDataTypeResolver implements DataTypeResolver {

	@Override
	public List<String> getDataTypeOptions() {
        return List.of(
                "INT", "BIGINT", "DECIMAL", "VARCHAR", "TEXT",
                "DATE", "DATETIME", "BOOLEAN", "JSON"
            );
	}
	
	
	@Override
	public List<String> getFkConstraintOptions() {
		return List.of(
			"CASCADE",
			"SET NULL",
			"RESTRICT",
			"NO ACTION"
		);
	}


	
}
