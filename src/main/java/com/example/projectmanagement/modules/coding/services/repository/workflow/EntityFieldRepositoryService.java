package com.example.projectmanagement.modules.coding.services.repository.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.projectmanagement.modules.databases.services.repository.DbTableRepositoryService;

/**
 * エンティティー専用のクラスフィールド永続化サービスです
 * クラスフィールドとエンティティーの関連付けや、とランズアクションを用いた連動処理ロジックを行います
 * @author yasufumimisono
 *
 */
@Service
public class EntityFieldRepositoryService {
	
	@Autowired
	private ModelGeneratorFactory modelFactory;
	
	@Autowired
	private ClassDefRepositoryService classDefRepoService;
	
	@Autowired
	private ClassFieldRepostitoryService fieldRepoService;
	
	@Autowired
	private EntityRepostitoryService entityRepoService;
	
	@Autowired
	private DbTableRepositoryService tableService;


	@Autowired
	private ClassDefRepositoryService classDefService;

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
		fieldRepoService.registerClassField(classField);
		
		EntityEntity entity = new EntityEntity(
				column.getId(),
				classField.getId(),
				projectId
				);
		
		entityRepoService.registerEntity(entity);
	}

	
	public void regenerateFieldFromColumn(TableColumnEntity col, String dbms) {
		EntityEntity entity = entityRepoService.findEntityByTableColId(col.getId());
		ClassFieldEntity originalField = fieldRepoService.findClassFieldById(entity.getFieldId());
		ClassDefinitionEntity classDef = classDefRepoService.findClassDefinitionById(originalField.getClassId());
		
		ModelGenerator modelGenerator = modelFactory.getGenerator(classDef.getLanguage());
		ClassFieldEntity newRepo = modelGenerator.createFieldFromDBColumn(col, classDef.getId(), dbms);
		newRepo.setId(originalField.getId());
		
		fieldRepoService.updateClassField(newRepo);
	}
}
