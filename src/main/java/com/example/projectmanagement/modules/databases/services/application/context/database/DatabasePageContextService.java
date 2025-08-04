package com.example.projectmanagement.modules.databases.services.application.context.database;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableColumnJoinedDto;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.services.application.context.AbstractPageContextService;

@Service("db")
public class DatabasePageContextService  extends AbstractPageContextService {


	public void setupDbListPage(Model model, Integer projectId, String title) {
		setProjectToModel(model, projectId);
		setPageTitle(model, title);
		
		List<DBInfoDtoRecord> dtoList = dbContext.getDbInfoDtoByProjectId(projectId);
		dbContext.setupDbList(model, dtoList);
		tableMultiContext.setupAllDatabaseTables(model, dtoList);
	}

	public void setupDbInfoDetailPage(Model model, Integer databaseId, Integer projectId, String title) {
		setProjectToModel(model, projectId);
		setPageTitle(model, title);
		

		DBInfoDtoRecord dto = dbContext.getDbInfoDtoById(databaseId);
		dbContext.setupDbInfo(model, dto);
//		tableContext.setupTableListlByDbId(model, databaseId);
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
		
		List<TableInfoDtoRecord> tables =  tableContext.getTableDtoListByDbId(databaseId);
		List<Long> tableIdList = tables.stream()
			    .map(TableInfoDtoRecord::id)
			    .collect(Collectors.toList());
		List<TableColumnJoinedDto> columns = columnContext.getTableColumnJoinedDtoListByTableIdList(tableIdList);
		
		colMultiContext.setupDbDetailWithTablesAndColums(model, tables, columns);
		
	}

	public void setupTableDefPrintablePage(Model model, Integer projectId, Integer databaseId) {
		setProjectToModel(model, projectId);
		DBInfoDtoRecord dto = dbContext.getDbInfoDtoById(databaseId);
		dbContext.setupDbInfo(model, dto);
		List<TableInfoDtoRecord> tableDtoList = tableContext.getTableDtoListByDbId(databaseId);
		List<Long> tableIds = tableContext.extractTableIds(tableDtoList);
		List<TableColumnJoinedDto> columns = columnContext.getTableColumnJoinedDtoListByTableIdList(tableIds);
		columnMultiContext.setupDbDetailWithTablesAndColums(model, tableDtoList, columns);
	}
}
