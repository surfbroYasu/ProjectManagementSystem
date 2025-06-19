package com.example.projectmanagement.modules.coding.langgenerator.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.generalutil.CaseConverter;
import com.example.projectmanagement.modules.coding.domain.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.domain.models.FieldModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;

@Component("javaModel")
public class JavaModelGenerator implements ModelGenerator {

	@Override
	public String dataTypeConverter(String dataType, String dbms) {
		if (dataType == null || dbms == null)
			return "String";

		String normalized = dataType.toLowerCase().split("\\(")[0].trim();
		String db = dbms.toLowerCase();

		switch (db) {
		case "mariadb":
		case "mysql":
			return convertFromMariaDB(normalized);
		case "postgresql":
			return convertFromPostgreSQL(normalized);
		default:
			return "String";
		}
	}

	@Override
	public ClassDefinitionModel createEntityClassFromDBTable(DBInfo dbInfo, TableInfo tableInfo, List<TableColumn> columnList,
			String dataUseType) {
		ClassDefinitionModel dto = new ClassDefinitionModel();
		dto.setClassName(CaseConverter.toPascalCase(tableInfo.getTableName()));
		dto.setClassAlias(tableInfo.getTableAlias());
		dto.setDescription(tableInfo.getTableAlias());
		dto.setDataUseType(dataUseType);
		dto.setLangage("Java");
		dto.setProjectId(dbInfo.getProjectId());

		List<FieldModel> fields = new ArrayList<>();
		for (TableColumn col : columnList) {
			FieldModel f = new FieldModel();
			f.setFieldName(CaseConverter.toCamelCase(col.getColumnName()));
			f.setDataType(dataTypeConverter(col.getDataType(), dbInfo.getDbms()));
			f.setTableColId(col.getId());
			fields.add(f);
		}
		dto.setFields(fields);
		return dto;
	}

	@Override
	public String stringBuilder(ClassDefinitionModel classDefinition) {
		StringBuilder sb = new StringBuilder();
		sb.append("public class " + classDefinition.getClassName() + "{\n");

		for (FieldModel f : classDefinition.getFields()) {
			//	とりあえずインデントは４文字
			sb.append("    private " + f.getDataType() + " " + f.getFieldName() + ";\n");
		}
		
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String convertFromMariaDB(String type) {
		switch (type) {
		case "int":
		case "integer":
			return "Integer";
		case "bigint":
			return "Long";
		case "decimal":
		case "double":
		case "float":
			return "Double";
		case "tinyint":
			return "Boolean";
		case "date":
			return "LocalDate";
		case "datetime":
		case "timestamp":
			return "LocalDateTime";
		case "char":
		case "varchar":
		case "text":
			return "String";
		default:
			return "String";
		}
	}

	@Override
	public String convertFromPostgreSQL(String type) {
		switch (type) {
		case "serial":
		case "integer":
			return "Integer";
		case "bigserial":
		case "bigint":
			return "Long";
		case "numeric":
		case "decimal":
		case "double precision":
			return "Double";
		case "boolean":
			return "Boolean";
		case "date":
			return "LocalDate";
		case "timestamp":
		case "timestamptz":
			return "LocalDateTime";
		case "character varying":
		case "text":
			return "String";
		default:
			return "String";
		}
	}

}
