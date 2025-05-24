package com.example.projectmanagement.modules.coding.langgenerator;

import java.util.List;

import com.example.projectmanagement.modules.coding.domain.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;

public interface ModelGenerator {

	public String dataTypeConverter(String dataType, String dbms);
	public ClassDefinitionModel createEntityClassFromDBTable(DBInfo dbInfo, TableInfo tableInfo, List<TableColumn> columnList, String dataUseType);
	public String stringBuilder(ClassDefinitionModel classDefinition);
	
	public String convertFromMariaDB(String type);
	public String convertFromPostgreSQL(String type);
	
}
