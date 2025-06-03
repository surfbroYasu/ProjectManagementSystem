package com.example.projectmanagement.modules.coding.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.coding.domain.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableService;
import com.example.projectmanagement.modules.projects.services.ProjectViewContextService;
@Service
public class ClassDefContextService extends ProjectViewContextService {

	@Autowired
	private DatabaseService dbService;
	
	@Autowired
	private DbTableService tableService;
	
	@Autowired
	private DbTableColumnService columnService;

	@Autowired
	private ModelGeneratorFactory modelFactory;
	
	public void setEntityView(Model model, String lang, Integer tableId, String dataUseType) {

		TableInfo table = tableService.getTableByTableId(tableId);
		DBInfo db = dbService.getDBInfoByDBId(table.getDbInfoId());
		List<TableColumn> columnList = columnService.getTableColumns(List.of(tableId));
		
		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassDefinitionModel classDef =  modelGenerator.createEntityClassFromDBTable(db, table, columnList, dataUseType);
		
		model.addAttribute("entity", modelGenerator.stringBuilder(classDef));
		
		setProjectToModel(model, db.getProjectId());
	}
}
