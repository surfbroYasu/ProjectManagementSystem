package com.example.projectmanagement.modules.coding.services.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefFieldsModel;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassFieldModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.modules.coding.services.repository.ClassDefRepositoryService;
import com.example.projectmanagement.modules.coding.services.repository.ClassFieldRepostitoryService;
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
	private ModelGeneratorFactory modelFactory;

	@Autowired
	private DatabaseService dbService;
	
	@Autowired
	private DbTableService tableService;
	
	@Autowired
	private DbTableColumnService columnService;
	
	@Autowired
	private ClassDefRepositoryService classRepoService;
	
	@Autowired
	private ClassFieldRepostitoryService fieldRepoService;
	
	/**
	 * テーブルとそのカラムをもとに、エンティティーを自動生成し、コードブロックを作成する
	 * @param model
	 * @param lang
	 * @param tableId
	 * @param dataUseType
	 */
	public void setEntityViewFromDb(Model model, String lang, Integer tableId, String dataUseType, String pageTitle) {

		TableInfo table = tableService.getTableByTableId(tableId);
		DBInfo db = dbService.getDBInfoByDBId(table.getDbInfoId());
		List<TableColumn> columnList = columnService.getTableColumns(List.of(tableId));
		
		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassDefFieldsModel classDef =  modelGenerator.createClassAndFieldsFromDBTable(db, table, columnList, dataUseType);
		
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
		model.addAttribute("title", pageTitle);
	}
	
	
	/**
	 * すでに登録済みのエンティティーからコードブロックを生成する。
	 * @param model
	 * @param tableId
	 */
	public void setEntityViewFromSavedEntity(Model model, Integer tableId, String pageTitle) {
		
		
		ClassDefinitionEntity classDef = classRepoService.findClassDefinitionByTableId(tableId);
		
		List<ClassFieldEntity>fieldList =  fieldRepoService.findnFieldsByClassId(classDef.getId());
	

		ClassDefFieldsModel classDefModel = new ClassDefFieldsModel();
		
		BeanUtils.copyProperties(classDef, classDefModel);
		
		List<ClassFieldModel> fieldModelList = new ArrayList<>();
		for (ClassFieldEntity item : fieldList) {
			ClassFieldModel itemModel = new ClassFieldModel();
			BeanUtils.copyProperties(item, itemModel);
			fieldModelList.add(itemModel);
		}
		
		classDefModel.setFields(fieldModelList);

		ModelGenerator modelGenerator = modelFactory.getGenerator(classDef.getLanguage());
		
		model.addAttribute("entity", modelGenerator.stringBuilder(classDefModel));
		model.addAttribute(pageTitle);
		setProjectToModel(model, classDef.getProjectId());
		model.addAttribute("title", pageTitle);
	}
}
