package com.example.projectmanagement.modules.coding.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.services.repository.DatabaseService;
import com.example.projectmanagement.modules.databases.services.repository.DbTableColumnService;
import com.example.projectmanagement.modules.databases.services.repository.DbTableService;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		ClassDefinitionModel classDef =  modelGenerator.createClassAndFieldsFromDBTable(db, table, columnList, dataUseType);
		
		model.addAttribute("entity", modelGenerator.stringBuilder(classDef));
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonClassDef;
		try {
			jsonClassDef = mapper.writeValueAsString(classDef);
			model.addAttribute("classDefJson", jsonClassDef);
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		
		setProjectToModel(model, db.getProjectId());
	}
}
