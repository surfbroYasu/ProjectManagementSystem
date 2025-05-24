package com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.mariadb;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnStructureValidationResult;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnStructureValidator;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnValidationMessageKeyEnum;

@Component("mariadbValidator")
public class MariaDbValidator implements ColumnStructureValidator {

	@Override
	public ColumnStructureValidationResult validateFkConstraint(boolean isNullable, String fkConstraint,
			String action) {
		if ("SET NULL".equalsIgnoreCase(fkConstraint) && !isNullable) {
			return ColumnStructureValidationResult.error(
					"onDelete", // 対象フィールド
					ColumnValidationMessageKeyEnum.FK_SETNULL_REQUIRES_NULLABLE,
					fkConstraint, action);
		}
		return ColumnStructureValidationResult.ok();
	}

	@Override
	public ColumnStructureValidationResult validateDefaultValue(String dataType, String defaultValue) {
		if (defaultValue == null || defaultValue.isBlank()) {
			return ColumnStructureValidationResult.ok();
		}

		String type = dataType.toLowerCase();

		if ("timestamp".equals(type)) {
			return ColumnStructureValidationResult.error(
					"defaultValue",
					ColumnValidationMessageKeyEnum.DEFAULT_TIMESTAMP_NOT_ALLOWED,
					dataType);
		}

		try {
			switch (type) {
			case "int":
			case "integer":
			case "bigint":
			case "smallint":
			case "tinyint":
				Integer.parseInt(defaultValue);
				return ColumnStructureValidationResult.ok();

			case "decimal":
			case "float":
			case "double":
			case "real":
				Double.parseDouble(defaultValue);
				return ColumnStructureValidationResult.ok();

			case "boolean":
			case "bool":
				String val = defaultValue.toLowerCase();
				if (val.equals("true") || val.equals("false") || val.equals("1") || val.equals("0")) {
					return ColumnStructureValidationResult.ok();
				}
				return ColumnStructureValidationResult.error(
						"defaultValue",
						ColumnValidationMessageKeyEnum.DEFAULT_INVALID_BOOLEAN,
						dataType);

			case "date":
				LocalDate.parse(defaultValue);
				return ColumnStructureValidationResult.ok();

			case "datetime":
				LocalDateTime.parse(defaultValue);
				return ColumnStructureValidationResult.ok();

			case "char":
			case "varchar":
			case "text":
				return ColumnStructureValidationResult.ok();

			default:
				return ColumnStructureValidationResult.error(
						"defaultValue",
						ColumnValidationMessageKeyEnum.DEFAULT_UNSUPPORTED_TYPE,
						dataType);
			}
		} catch (Exception e) {
			return ColumnStructureValidationResult.error(
					"defaultValue",
					ColumnValidationMessageKeyEnum.DEFAULT_PARSE_ERROR,
					defaultValue, dataType);
		}
	}
}
