package com.example.projectmanagement.modules.databases.services.application.context.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.ColumnDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.services.application.context.column.DbColumnContextService;
import com.example.projectmanagement.modules.databases.services.application.context.multi.ColumnMultiContextService;
import com.example.projectmanagement.modules.databases.services.application.context.multi.TableMultiContextService;
import com.example.projectmanagement.modules.databases.services.application.context.table.DbTableContextService;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;

@Service
public class DatabasePageContextService  extends ProjectViewContextService {

	@Autowired
	private DbInfoContextService dbContext;

	@Autowired
	private DbColumnContextService columnContext;
	
	@Autowired
	private DbTableContextService tableContext;
	
	@Autowired
	private TableMultiContextService tableMultiContext;
	@Autowired
	private ColumnMultiContextService columMultiContext;

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
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
	}

	public void setupTableDefPrintablePage(Model model, Integer databaseId) {
		DBInfoDtoRecord dto = dbContext.getDbInfoDtoById(databaseId);
		dbContext.setupDbInfo(model, dto);
		List<TableInfoDtoRecord> tableDtoList = tableContext.getTableDtoListByDbId(databaseId);
		List<Long> tableIds = tableContext.extractTableIds(tableDtoList);
		List<ColumnDtoRecord> columns = columnContext.getColumnDtoListByTableIdList(tableIds);
		columMultiContext.setupDbDetailWithTablesAndColums(model, tableDtoList, columns);
	}
}
