package com.example.projectmanagement.modules.coding.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.EntityEntity;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.modules.coding.services.repository.ClassDefRepositoryService;
import com.example.projectmanagement.modules.coding.services.repository.ClassFieldRepostitoryService;
import com.example.projectmanagement.modules.coding.services.repository.EntityRepostitoryService;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;
import com.example.projectmanagement.modules.databases.services.repository.DatabaseRepositoryService;
import com.example.projectmanagement.modules.databases.services.repository.DbColumnRepositoryService;
import com.example.projectmanagement.modules.databases.services.repository.DbTableRepositoryService;

@Service
public class EntityFieldService {

	@Autowired
	private DatabaseRepositoryService dbService;

	@Autowired
	private DbTableRepositoryService tableService;

	@Autowired
	private DbColumnRepositoryService columnService;

	@Autowired
	private ModelGeneratorFactory modelFactory;
	
	@Autowired
	private ClassDefRepositoryService classDefService;
	
	@Autowired
	private ClassFieldRepostitoryService fieldService;
	
	@Autowired
	private EntityRepostitoryService entityService;

	
	@Transactional
//	public void registerEntitiesFromTableId(String lang, Integer projectId, Integer tableId, String dataUseType) {
//
//		TableInfo table = tableService.getTableByTableId(tableId);
//		DBInfo db = dbService.getDBInfoByDBId(table.getDbInfoId());
//		List<TableColumn> dbColumnList = columnService.getTableColumns(List.of(tableId));
//
//		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
//		ClassDefFieldsModel classDef = modelGenerator.createClassAndFieldsFromDBTable(db, table, dbColumnList,
//				dataUseType);
//
//		ClassDefinitionEntity classDefEntity = new ClassDefinitionEntity();
//		BeanUtils.copyProperties(classDef, classDefEntity);
//		
//		classDefService.registerClassDef(classDefEntity);
//
//		int classId = classDefEntity.getId();
//
//		for (ClassFieldModel each : classDef.getFields()) {
//
//			ClassFieldEntity classFieldEntity = new ClassFieldEntity(each.getFieldName(), each.getDataType(), classId);
//			fieldService.registerClassField(classFieldEntity);
//
//			EntityEntity entityEntity = new EntityEntity(each.getTableColId(), each.getId(), projectId);
//			entityService.registerEntity(entityEntity);
//		}
//	}
	
	public void createClassDefFromTableId(String lang, Integer projectId, Integer tableId, String dataUseType) {
		TableInfoEntity table = tableService.getTableByTableId(tableId);
		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassDefinitionEntity domain = modelGenerator.createClassFromDBTable(projectId, table, dataUseType);
		domain.setTableId(tableId);
		
		classDefService.registerClassDef(domain);
	}
	
	public void createEntityFieldFromTableCol(String lang, Integer projectId, String dbms, Integer classId, TableColumnEntity column) {

		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassFieldEntity classField = modelGenerator.createFieldFromDBColumn(column, classId, dbms);
		fieldService.registerClassField(classField);
		
		EntityEntity entity = new EntityEntity(
				column.getId(),
				classField.getId(),
				projectId
				);
		
		entityService.registerEntity(entity);
	}

	
}
