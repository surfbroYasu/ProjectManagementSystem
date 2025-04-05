package com.example.projectmanagement.persistence.modules.databases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableInfo;

@Mapper
public interface DBInfoMapper {

	public List<DBInfo> getDBInfoByProject(int projectId);
	
	public void insertNewDatabase(DBInfo domain);
	
	
	public DBInfo getDBInfoByDBId(int dbId);

	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds);

	public TableInfo getTableByTableId(int tableId);

	public void insertNewTable(TableInfo domain);
}
