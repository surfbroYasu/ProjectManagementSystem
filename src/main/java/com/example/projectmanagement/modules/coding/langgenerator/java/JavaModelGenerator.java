package com.example.projectmanagement.modules.coding.langgenerator.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.projectmanagement.generalutil.CaseConverter;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefFieldsModel;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassFieldModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;

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
	public ClassDefFieldsModel createClassAndFieldsFromDBTable(DBInfoEntity dbInfo, TableInfoEntity tableInfo,
			List<TableColumnEntity> columnList,
			String dataUseType) {

		ClassDefinitionEntity classDefEntity = createClassFromDBTable(dbInfo.getProjectId(), tableInfo, dataUseType);
		ClassDefFieldsModel dto = new ClassDefFieldsModel();
		BeanUtils.copyProperties(classDefEntity, dto);
		
		List<ClassFieldModel> fields = new ArrayList<>();
		for (TableColumnEntity col : columnList) {
			ClassFieldModel f = new ClassFieldModel();
			f.setFieldName(CaseConverter.toCamelCase(col.getColumnName()));
			f.setDataType(dataTypeConverter(col.getDataType(), dbInfo.getDbms()));
			fields.add(f);
		}
		dto.setFields(fields);
		return dto;
	}

	@Override
	public ClassDefinitionEntity createClassFromDBTable(Integer projectId, TableInfoEntity tableInfo, String dataUseType) {
		return new ClassDefinitionEntity(
				CaseConverter.toPascalCase(tableInfo.getTableName()),
				tableInfo.getTableAlias(),
				tableInfo.getTableAlias(),
				dataUseType,
				"Java",
				projectId
				);
	}
	
	@Override
	public ClassFieldEntity createFieldFromDBColumn(TableColumnEntity column, Integer classId, String dbms) {
		return new ClassFieldEntity(
				CaseConverter.toCamelCase(column.getColumnName()),
				dataTypeConverter(column.getDataType(), dbms),
				classId
				);
	}

	@Override
	public String stringBuilder(ClassDefFieldsModel classDefinition) {
		StringBuilder sb = new StringBuilder();
		sb.append("public class " + classDefinition.getClassName() + "{\n");

		for (ClassFieldModel f : classDefinition.getFields()) {
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
