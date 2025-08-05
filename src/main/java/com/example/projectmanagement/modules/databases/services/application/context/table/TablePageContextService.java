package com.example.projectmanagement.modules.databases.services.application.context.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableColumnJoinedDto;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.services.application.context.AbstractPageContextService;
import com.example.projectmanagement.modules.databases.services.application.context.SqlContextService;

@Service("table")
public class TablePageContextService extends AbstractPageContextService{
	
	@Autowired
	private SqlContextService sqlContext;

	
	public void setupTableDetailPage(Model model, Integer projectId, Integer databaseId, Long tableId, String title) {
		setProjectToModel(model, projectId);
		setPageTitle(model, title);

		DBInfoDtoRecord dbms = dbContext.getDbInfoDtoById(projectId);
		dbContext.setupDbInfo(model, dbms);
		
		TableInfoDtoRecord tableDto = tableContext.getTableDtoById(tableId);
		List<TableColumnJoinedDto> columns = columnContext.getColumnDtoListByTableId(tableId);
		
		colMultiContext.setTableDetailWithColumns(model, tableDto, columns);
		
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
		sqlContext.setAllSQLtoModel(model, title, tableId);
	}

	public void setupTableDefPrintable(Model model, Integer projectId, Integer databaseId, Long tableId) {
		setProjectToModel(model, projectId);
		DBInfoDtoRecord dbms = dbContext.getDbInfoDtoById(projectId);
		dbContext.setupDbInfo(model, dbms);
		
		TableInfoDtoRecord tableDto = tableContext.getTableDtoById(tableId);
		List<TableColumnJoinedDto> columns = columnContext.getColumnDtoListByTableId(tableId);
		
		columnMultiContext.setTableDetailWithColumns(model, tableDto, columns);
		columnContext.prepareColumnForm(model, databaseId, dbContext.findDbms(databaseId));
		model.addAttribute("print", "print");
	}

}
