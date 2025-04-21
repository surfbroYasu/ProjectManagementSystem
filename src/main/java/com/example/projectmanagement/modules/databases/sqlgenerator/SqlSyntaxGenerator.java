package com.example.projectmanagement.modules.databases.sqlgenerator;

import java.util.List;
import java.util.Map;

import com.example.projectmanagement.modules.databases.domain.TableColumn;
import com.example.projectmanagement.modules.databases.domain.TableInfo;

public interface SqlSyntaxGenerator {

	String createTable(TableInfo table, List<TableColumn> columns);
	String addColumn(String tableName, TableColumn column);
	String dropTable(String tableName);
	
	String insertTemplate(String tableName, List<String> columnNames);
	String insertStatement(String tableName, List<String> columnNames, List<Map<String, Object>> records);
	
	String updateStatement(String tableName, Map<String, Object> updates, String whereClause);
	String updateTemplate(String tableName, List<String> columnNames, String whereClause);
	
	String deleteStatement(String tableName, String whereClause);
	String deleteTemplate(String tableName, String whereClause);


	
}
