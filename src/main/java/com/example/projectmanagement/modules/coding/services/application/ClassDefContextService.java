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
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;
import com.example.projectmanagement.modules.databases.repository.DbColumnJpaRepository;
import com.example.projectmanagement.modules.databases.repository.DbInfoJpaRepository;
import com.example.projectmanagement.modules.databases.repository.DbTableJpaRepository;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service("classDefService")
public class ClassDefContextService extends ProjectViewContextService {

	@Autowired
	private ModelGeneratorFactory modelFactory;

	@Autowired
	private DbInfoJpaRepository dbService;
	
	@Autowired
	private DbTableJpaRepository tableService;
	
	@Autowired
	private DbColumnJpaRepository columnService;
	
//	@Autowired
//	private DatabaseRepositoryService dbService;
//	
//	@Autowired
//	private DbTableRepositoryService tableService;
//	
//	@Autowired
//	private DbColumnRepositoryService columnService;
//	
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
	public void setEntityViewFromDb(Model model, String lang, Long tableId, String dataUseType, String pageTitle) {

		TableInfoEntity table = tableService.findById(tableId).orElseThrow();
		DBInfoEntity db = dbService.findById(table.getDbInfoId()).orElseThrow();
		List<TableColumnEntity> columnList = columnService.findAllBytableInfoId(tableId);
		
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
