package com.example.projectmanagement.modules.coding.langgenerator.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.projectmanagement.generalutil.CaseConverter;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassField;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.datastructure.models.FieldModel;
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

		String db = dbms.toLowerCase();

		switch (db) {
		case "mariadb":
		case "mysql":
			return convertFromMariaDB(dataType);
		case "postgresql":
			return convertFromPostgreSQL(dataType);
		default:
			return "String";
		}
	}

	@Override
	public ClassDefinitionModel createClassAndFieldsFromDBTable(DBInfo dbInfo, TableInfo tableInfo,
			List<TableColumn> columnList,
			String dataUseType) {

		ClassDefinition classDefEntity = createClassFromDBTable(dbInfo.getProjectId(), tableInfo, dataUseType);
		ClassDefinitionModel dto = new ClassDefinitionModel();
		BeanUtils.copyProperties(classDefEntity, dto);
		
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
	public ClassDefinition createClassFromDBTable(Integer projectId, TableInfo tableInfo, String dataUseType) {
		return new ClassDefinition(
				CaseConverter.toPascalCase(tableInfo.getTableName()),
				tableInfo.getTableAlias(),
				tableInfo.getTableAlias(),
				dataUseType,
				"Java",
				projectId
				);
	}
	
	@Override
	public ClassField createFieldFromDBColumn(TableColumn column, Integer classId, String dbms) {
		return new ClassField(
				CaseConverter.toCamelCase(column.getColumnName()),
				dataTypeConverter(column.getDataType(), dbms),
				classId
				);
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
		switch (type.toLowerCase()) {
		case "int":
		case "integer":
			return "Integer";
		case "int unsigned":
		case "bigint":
			return "Long";
		case "bigint unsigned":
			return "BigInteger";
		case "decimal":
		case "double":
		case "float":
			return "Double";
		case "tinyint":
		case "boolean":
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
		switch (type.toLowerCase()) {
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
