package com.example.projectmanagement.modules.databases.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableColumn;
import com.example.projectmanagement.modules.databases.domain.TableInfo;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DatabaseService {

	@Autowired
	private DBInfoMapper mapper;

	/*
	 * ----DB INFO----
	 */
	
	public void insertDatabase(DBInfo domain) {
		mapper.insertNewDatabase(domain);
	}

	public List<DBInfo> getAll(int projectId) {
		return mapper.getDBInfoByProject(projectId);
	}
	
	public DBInfo getDBInfoByDBId(int dbId) {
		return mapper.getDBInfoByDBId(dbId);
	}

	/*
	 * ----TABLES----
	 */

	public void insertTable(TableInfo domain) {
		mapper.insertNewTable(domain);
	}
	
	public TableInfo getTableByTableId(int tableId) {
		return mapper.getTableByTableId(tableId);
	}
	
	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds) {
		return mapper.getTableInfoByDbIds(dbInfoIds);
	}
		
	public Map<Integer, List<TableInfo>> getRelatedTableInfo(List<DBInfo> databases) {
	    List<Integer> dbInfoIds = databases.stream()
	                                       .map(DBInfo::getId)
	                                       .collect(Collectors.toList());

	    List<TableInfo> tableInfos = mapper.getTableInfoByDbIds(dbInfoIds);

	    return tableInfos.stream()
	                     .collect(Collectors.groupingBy(TableInfo::getDbInfoId));
	}

	
	/*
	 * ----COLUMNS----
	 */
	public void insertColumn(TableColumn domain) {
		mapper.insertNewColumn(domain);
	}
	
	public List<TableColumn> getFKList(int dbId){
		return mapper.getFKList(dbId);
	}
	
	public List<TableColumn> getTableColumns(List<Integer> tableIds){
		return mapper.getColumnsByTableIds(tableIds);
	}
	
	public Map<Integer, List<TableColumn>> getRelatedColumns(List<TableInfo> tables){
		List<Integer> tableIds = tables.stream()
				.map(TableInfo::getId)
				.collect(Collectors.toList());
		
		List<TableColumn> relatedColumns = mapper.getColumnsByTableIds(tableIds);
		
		return relatedColumns.stream().collect(Collectors.groupingBy(TableColumn::getTableInfoId));
	}


}
