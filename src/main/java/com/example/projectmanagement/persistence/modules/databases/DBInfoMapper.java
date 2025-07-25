package com.example.projectmanagement.persistence.modules.databases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;

@Mapper
public interface DBInfoMapper {
	
	/*
	 * database
	 */
	public void insertNewDatabase(DBInfoEntity domain);
	public void updateDatabase(DBInfoEntity domain);
	public void deleteDatabase(int dbId);
	
	public DBInfoEntity getDBInfoByDBId(int dbId);
	public List<DBInfoEntity> getDBInfoByProject(int projectId);
		
	
	
	/*
	 * tables
	 */
	public void insertNewTable(TableInfoEntity domain);
	public void updateTable(TableInfoEntity domain);
	public void deleteTable(int tableId);
	
	public TableInfoEntity getTableByTableId(int tableId);
	public List<TableInfoEntity> getTableInfoByDbIds(List<Integer> dbInfoIds);

	/*
	 * columns
	 */
	public void insertNewColumn(TableColumnEntity domain);
	public void updateColumn(TableColumnEntity domain);
	public void deleteColumn(int columnId);
	public List<TableColumnEntity> getFKList(int dbId);
	public List<TableColumnEntity> getColumnsByTableIds(List<Integer> tableIds);
	
}
