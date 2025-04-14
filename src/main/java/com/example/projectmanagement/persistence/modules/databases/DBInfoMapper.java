package com.example.projectmanagement.persistence.modules.databases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableColumn;
import com.example.projectmanagement.modules.databases.domain.TableInfo;

@Mapper
public interface DBInfoMapper {
	
	/*
	 * database
	 */
	public void insertNewDatabase(DBInfo domain);
	public void updateDatabase(DBInfo domain);
	public void deleteDatabase(int dbId);
	
	public DBInfo getDBInfoByDBId(int dbId);
	public List<DBInfo> getDBInfoByProject(int projectId);
		
	
	
	/*
	 * tables
	 */
	public void insertNewTable(TableInfo domain);
	public void updateTable(TableInfo domain);
	public void deleteTable(int tableId);
	
	public TableInfo getTableByTableId(int tableId);
	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds);

	/*
	 * columns
	 */
	public void insertNewColumn(TableColumn domain);
	public void updateColumn(TableColumn domain);
	public void deleteColumn(int columnId);
	public List<TableColumn> getFKList(int dbId);
	public List<TableColumn> getColumnsByTableIds(List<Integer> tableIds);
	
}
