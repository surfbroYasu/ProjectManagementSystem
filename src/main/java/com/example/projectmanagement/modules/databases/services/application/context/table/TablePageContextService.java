package com.example.projectmanagement.modules.databases.services.application.context.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.ColumnDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.services.application.context.SqlContextService;
import com.example.projectmanagement.modules.databases.services.application.context.column.DbColumnContextService;
import com.example.projectmanagement.modules.databases.services.application.context.database.DbInfoContextService;
import com.example.projectmanagement.modules.databases.services.application.context.multi.ColumnMultiContextService;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;

@Service
public class TablePageContextService extends ProjectViewContextService{
	
	@Autowired
	private DbInfoContextService dbContext;
	@Autowired
	private SqlContextService sqlContext;
	@Autowired
	private DbTableContextService tableContext;
	@Autowired
	private DbColumnContextService columnContext;
	@Autowired
	private ColumnMultiContextService columMultiContext;
	
	public void setupTableDetailPage(Model model, Integer projectId, Integer databaseId, Long tableId, String title) {
		setProjectToModel(model, projectId);
		setPageTitle(model, title);

		DBInfoDtoRecord dbms = dbContext.getDbInfoDtoById(projectId);
		dbContext.setupDbInfo(model, dbms);
		TableInfoDtoRecord tableDto = tableContext.getTableDtoById(tableId);
		tableContext.setupTableDetail(model, tableDto);
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
		sqlContext.setAllSQLtoModel(model, title, tableId);
	}

	public void setupTableDefPrintable(Model model, Integer projectId, Integer databaseId, Long tableId) {

		DBInfoDtoRecord dbms = dbContext.getDbInfoDtoById(projectId);
		dbContext.setupDbInfo(model, dbms);
		
		TableInfoDtoRecord tableDto = tableContext.getTableDtoById(tableId);
		List<ColumnDtoRecord> columns = columnContext.getColumnDtoListByTableId(tableId);
		
		columMultiContext.setTableDetailWithColumns(model, tableDto, columns);
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
		model.addAttribute("print", "print");
	}

}
