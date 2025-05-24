package com.example.projectmanagement.modules.databases.services.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.persistence.DBInfoMapper;

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

	public void updateDatabase(DBInfo domain) {
		mapper.updateDatabase(domain);
	}

	public void deleteDatabase(int dbId) {
		mapper.deleteDatabase(dbId);
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

	public void updateTable(TableInfo domain) {
		mapper.updateTable(domain);
	}

	public void deleteTable(int tableId) {
		mapper.deleteTable(tableId);
	}

	public TableInfo getTableByTableId(int tableId) {
		return mapper.getTableByTableId(tableId);
	}

	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds) {
		return mapper.getTableInfoByDbIds(dbInfoIds);
	}

//	public Map<Integer, List<TableInfo>> getRelatedTableInfo(List<DBInfo> databases) {
//		List<Integer> dbInfoIds = databases.stream()
//				.map(DBInfo::getId)
//				.collect(Collectors.toList());
//
//		List<TableInfo> tableInfos = mapper.getTableInfoByDbIds(dbInfoIds);
//
//		return tableInfos.stream()
//				.collect(Collectors.groupingBy(TableInfo::getDbInfoId));
//	}

	/*
	 * ----COLUMNS----
	 */
	public void insertColumn(TableColumn domain) {
		intParamChecker(domain);
		mapper.insertNewColumn(domain);
	}

	public void updateColumn(TableColumn domain) {
		intParamChecker(domain);
		mapper.updateColumn(domain);
	}

	public void deleteColumn(int columnId) {
		mapper.deleteColumn(columnId);
	}

	public List<TableColumn> getFKList(int dbId) {
		return mapper.getFKList(dbId);
	}

	public List<TableColumn> getTableColumns(List<Integer> tableIds) {
		return mapper.getColumnsByTableIds(tableIds);
	}

	public Map<Integer, List<TableColumn>> getRelatedColumns(List<TableInfo> tables) {
		List<Integer> tableIds = tables.stream()
				.map(TableInfo::getId)
				.collect(Collectors.toList());

		List<TableColumn> relatedColumns = mapper.getColumnsByTableIds(tableIds);

		return relatedColumns.stream().collect(Collectors.groupingBy(TableColumn::getTableInfoId));
	}

	private TableColumn intParamChecker(TableColumn domain) {
		switch (domain.getDataType()) {
		case "INT":			
			if (domain.getDataTypeParam().isBlank()) {
				domain.setDataTypeParam("11");
			}
			break;

		case "TINYINT":
			if (domain.getDataTypeParam().isBlank()) {
				domain.setDataTypeParam("1");
			}
			break;

		default:
			break;
		}
		return domain;
	}

}
