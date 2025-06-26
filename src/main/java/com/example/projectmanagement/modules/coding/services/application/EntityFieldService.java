package com.example.projectmanagement.modules.coding.services.application;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassField;
import com.example.projectmanagement.modules.coding.datastructure.entity.Entity;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.datastructure.models.FieldModel;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGenerator;
import com.example.projectmanagement.modules.coding.langgenerator.ModelGeneratorFactory;
import com.example.projectmanagement.modules.coding.services.domain.ClassDefDomainService;
import com.example.projectmanagement.modules.coding.services.domain.ClassFieldDomainService;
import com.example.projectmanagement.modules.coding.services.domain.EntityDomainService;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableService;

@Service
public class EntityFieldService {

	@Autowired
	private DatabaseService dbService;

	@Autowired
	private DbTableService tableService;

	@Autowired
	private DbTableColumnService columnService;

	@Autowired
	private ModelGeneratorFactory modelFactory;
	
	@Autowired
	private ClassDefDomainService classDefService;
	
	@Autowired
	private ClassFieldDomainService fieldService;
	
	@Autowired
	private EntityDomainService entityService;

	
	@Transactional
	public void registerEntitiesFromTableId(String lang, Integer projectId, Integer tableId, String dataUseType) {

		TableInfo table = tableService.getTableByTableId(tableId);
		DBInfo db = dbService.getDBInfoByDBId(table.getDbInfoId());
		List<TableColumn> dbColumnList = columnService.getTableColumns(List.of(tableId));

		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassDefinitionModel classDef = modelGenerator.createClassAndFieldsFromDBTable(db, table, dbColumnList,
				dataUseType);

		ClassDefinition classDefEntity = new ClassDefinition();
		BeanUtils.copyProperties(classDef, classDefEntity);
		
		classDefService.registerClassDef(classDefEntity);

		int classId = classDefEntity.getId();

		for (FieldModel each : classDef.getFields()) {

			ClassField classFieldEntity = new ClassField(each.getFieldName(), each.getDataType(), classId);
			fieldService.registerClassField(classFieldEntity);

			Entity entityEntity = new Entity(each.getTableColId(), each.getId(), projectId);
			entityService.registerEntity(entityEntity);
		}
	}
	
	public void createClassDefFromTableId(String lang, Integer projectId, Integer tableId, String dataUseType) {
		TableInfo table = tableService.getTableByTableId(tableId);
		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassDefinition domain = modelGenerator.createClassFromDBTable(projectId, table, dataUseType);
		domain.setTableId(tableId);
		
		classDefService.registerClassDef(domain);
	}
	
	public void createEntityFieldFromTableCol(String lang, Integer projectId, String dbms, Integer classId, TableColumn column) {

		ModelGenerator modelGenerator = modelFactory.getGenerator(lang);
		ClassField classField = modelGenerator.createFieldFromDBColumn(column, classId, dbms);
		fieldService.registerClassField(classField);
		
		Entity entity = new Entity(
				column.getId(),
				classField.getId(),
				projectId
				);
		
		entityService.registerEntity(entity);
	}
	
	public void updateEntityField(TableColumn col, String dbms) {
		Entity entity = entityService.findEntityByTableColId(col.getId());
		ClassField originalField = fieldService.findClassFieldById(entity.getFieldId());
		ClassDefinition classDef = classDefService.findClassDefinitionById(originalField.getClassId());
		
		ModelGenerator modelGenerator = modelFactory.getGenerator(classDef.getLanguage());
		ClassField newDomain = modelGenerator.createFieldFromDBColumn(col, classDef.getId(), dbms);
		newDomain.setId(originalField.getId());
		
		fieldService.updateClassField(newDomain);
	}
	
}
