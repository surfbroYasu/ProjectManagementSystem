package com.example.projectmanagement.modules.databases.services.application.sqlgenerator.mariadb;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;

@Component("mariadbType")
public class MariaDbDataTypeResolver implements DataTypeResolver {

	@Override
	public List<String> getDataTypeOptions() {
        return List.of(
                "INT", "INT UNSIGNED", "BIGINT", "BIGINT UNSIGNED", "DECIMAL", "VARCHAR", "TEXT",
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

	@Override
	public TableColumn adjustDataTypeParam(TableColumn domain) {
		String type = domain.getDataType();
		String param = domain.getDataTypeParam();
		
		switch (type) {
			case "INT":
			case "INT UNSIGNED":
			case "BIGINT":
			case "BIGINT UNSIGNED":
			case "BOOLEAN":
			case "JSON":
			case "TEXT":
			case "DATE":
			case "DATETIME":
				domain.setDataTypeParam(""); 
				break;

			case "VARCHAR":
				if (param.isBlank()) {
					domain.setDataTypeParam("255");
				}
				break;

			case "DECIMAL":
				if (param.isBlank()) {
					domain.setDataTypeParam("10,2");
				}
				break;

			default:
				break;
		}

		return domain;
	}
	
}
