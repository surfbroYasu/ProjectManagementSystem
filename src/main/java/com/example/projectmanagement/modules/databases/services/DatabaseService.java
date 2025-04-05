package com.example.projectmanagement.modules.databases.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableInfo;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DatabaseService {

	@Autowired
	private DBInfoMapper mapper;

	/*
	 * ----DB INFO----
	 */

	public List<DBInfo> getAll(int projectId) {
		return mapper.getDBInfoByProject(projectId);
	}

	public void insertDatabase(DBInfo domain) {
		mapper.insertNewDatabase(domain);
	}
	
	public DBInfo getDBInfoByDBId(int id) {
		return mapper.getDBInfoByDBId(id);
	}

	/*
	 * ----TABLES----
	 */

	public void insertTable(TableInfo domain) {
		mapper.insertNewTable(domain);
	}
		
	public Map<Integer, List<TableInfo>> getRelatedTableInfo(List<DBInfo> databases) {
	    List<Integer> dbInfoIds = databases.stream()
	                                       .map(DBInfo::getId)
	                                       .collect(Collectors.toList());

	    List<TableInfo> tableInfos = mapper.getTableInfoByDbIds(dbInfoIds);

	    return tableInfos.stream()
	                     .collect(Collectors.groupingBy(TableInfo::getDbInfoId));
	}


	
	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds) {
		return mapper.getTableInfoByDbIds(dbInfoIds);
	}
	
	public TableInfo getTableByTableId(int tableId) {
		return mapper.getTableByTableId(tableId);
	}
}
