package com.example.projectmanagement.modules.databases.sqlgenerator.mariadb;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.modules.databases.sqlgenerator.DataTypeResolver;

@Component("mariadb")
public class MariaDbDataTypeResolver implements DataTypeResolver {

	@Override
	public List<String> getDataTypeOptions() {
        return List.of(
                "INT", "BIGINT", "DECIMAL", "VARCHAR", "TEXT",
                "DATE", "DATETIME", "BOOLEAN", "JSON"
            );
	}

	
}
