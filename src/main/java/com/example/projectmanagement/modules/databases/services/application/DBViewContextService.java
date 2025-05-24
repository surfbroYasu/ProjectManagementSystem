package com.example.projectmanagement.modules.databases.services.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.ColumnDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.projects.services.ProjectViewContextService;

@Component
public class DBViewContextService extends ProjectViewContextService {

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private DataTypeResolverFactory resolverFactory;

	private Map<Integer, List<TableInfoDtoRecord>> convertToTableDtoMap(List<TableInfo> tableInfos) {
		return tableInfos.stream()
				.map(this::tableInfoToDto)
				.collect(Collectors.groupingBy(TableInfoDtoRecord::dbInfoId));
	}
	
	private Map<Integer, List<ColumnDtoRecord>> convertToColumnDtoMap(List<TableColumn> columns) {
		return columns.stream()
				.map(this::columnInfoToDto)
				.collect(Collectors.groupingBy(ColumnDtoRecord::tableInfoId));
	}

	private DBInfoDtoRecord dbInfoToDto(DBInfo entity) {
		return new DBInfoDtoRecord(
				entity.getId(),
				entity.getProjectId(),
				entity.getDbName(),
				entity.getDbms());
	}

	private TableInfoDtoRecord tableInfoToDto(TableInfo entity) {
		return new TableInfoDtoRecord(
				entity.getId(),
				entity.getDbInfoId(),
				entity.getTableName(),
				entity.getTableAlias());
	}

	private ColumnDtoRecord columnInfoToDto(TableColumn entity) {
		return new ColumnDtoRecord(
				entity.getId(),
				entity.getTableInfoId(),
				entity.getColumnName(),
				entity.getAlias(),
				entity.getDataType(),
				entity.getDataTypeParam(),
				entity.getIsPrimary(),
				entity.getIsUnique(),
				entity.getIsForign(),
				entity.getIsNullable(),
				entity.getIsAutoIncrement(),
				entity.getDefaultValue(),
				entity.getForignId(),
				entity.getCheckConstraint(),
				entity.getComment(),
				entity.getOnDelete(),
				entity.getOnUpdate(),
				entity.getTableName(),
				entity.getRefTableName(),
				entity.getRefTableName());
	}

	/**
	 * プロジェクトに関連する全てのDBとそのテーブルを取得するロジック。
	 * 	<hr>
	 * Modelに追加されるキー(thymeleaf) ：オブジェクト（Java）<br>
	 * - "dbList" : ArrayList<DBInfoDtoRecord> <br>
	 * - "tableInfoMap" : Map<Integer, List<TableInfoDtoRecord>>
	 *<hr>
	 * 	
	 * @param model
	 * @param List<DBInfo> databases of a project
	 */
	public void setAllDatabaseTablesContext(Model model, List<DBInfo> dbs) {
		List<DBInfoDtoRecord> dbList = new ArrayList<>();
		for (DBInfo each : dbs) {
			DBInfoDtoRecord eachDto = dbInfoToDto(each);
			dbList.add(eachDto);
		}
		model.addAttribute("dbList", dbList);

		List<Integer> ids = dbs.stream()
				.map(DBInfo::getId)
				.collect(Collectors.toList());
		Map<Integer, List<TableInfoDtoRecord>> tablesWithRelatedDBId = convertToTableDtoMap(
				databaseService.getTableInfoByDbIds(ids));
		model.addAttribute("tableInfoMap", tablesWithRelatedDBId);
	}

	/**
	 * データベースIDをもとに、DB情報とプロジェクト情報を取得してModelに追加する。
	 *<hr>
	 * Modelに追加されるキー(thymeleaf) ：オブジェクト（Java）<br>
	 * - "project" : ProjectDtoRecord<br>
	 * - "dbDto" : DBInfoDtoRecord
	 *<hr>
	 *
	 * @param model Modelオブジェクト
	 * @param databaseId 対象のデータベースID
	 * @return DBInfoオブジェクト
	 */
	public DBInfo setDatabaseContext(Model model, Integer databaseId) {
		DBInfo db = databaseService.getDBInfoByDBId(databaseId);
		DBInfoDtoRecord dbDto = dbInfoToDto(db);

		setProjectToModel(model, db.getProjectId());
		model.addAttribute("db", dbDto);
		return db;
	}

	/**
	 * テーブルIDをもとに、TableInfo、DBInfo、Project情報を取得してModelに追加する。
	 *<hr>
	 * Modelに追加されるキー(thymeleaf) ：オブジェクト（Java）<br>
	 * - "project" : ProjectDtoRecord<br>
	 * - "table" : TableInfoDtoRecord
	 * <hr>
	 *
	 * @param model Modelオブジェクト
	 * @param tableId 対象のテーブルID
	 * @return TableInfoオブジェクト
	 */
	public void setDatabaseTablesContext(Model model, Integer databaseId) {
		
		List<TableInfo> relatedTables = databaseService.getTableInfoByDbIds(List.of(databaseId));		
		List <TableInfoDtoRecord> dtoTables = new ArrayList<>();
		for (TableInfo each : relatedTables) {
			dtoTables.add(tableInfoToDto(each));
		}
		model.addAttribute("tableList", dtoTables);
		
		List<Integer> ids = relatedTables.stream()
				.map(TableInfo::getId)
				.collect(Collectors.toList());
		
		Map<Integer, List<ColumnDtoRecord>> columnsWithRelatedTableId = convertToColumnDtoMap(
				databaseService.getTableColumns(ids));
		model.addAttribute("columnMap", columnsWithRelatedTableId);
		
	}
	
	public TableInfo setSingleTableContext(Model model, Integer tableId, List<TableColumn> columnList) {
		TableInfo table = databaseService.getTableByTableId(tableId);

		TableInfoDtoRecord tableDto = tableInfoToDto(table);
		model.addAttribute("table", tableDto);
		
		List<ColumnDtoRecord> columnListDto = new ArrayList<>();
		for (TableColumn each : columnList) {
			columnListDto.add(columnInfoToDto(each));
		}
		model.addAttribute("columnList", columnListDto);

		return table;
	}

	/**
	 * テーブルカラム登録フォームとデータ型解決オブジェクトをModelに追加する。
	 *<hr>
	 * Modelに追加されるキー(thymeleaf) ：オブジェクト（Java）<br>
	 * - "tableColumnRegisterForm" : TableColumnRegisterForm<br>
	 * - "dataTypeResolver": DataTypeResolver
	 *<hr>
	 * @param model Modelオブジェクト
	 * @param databaseId 外部キー取得用のDB ID
	 * @param dbms 対象DBMSの名前（例：mariadb, postgresql）
	 */
	public void prepareColumnFormForModel(Model model, Integer databaseId, String dbms) {
		TableColumnRegisterForm form = new TableColumnRegisterForm();
		form.setForignOptions(databaseService.getFKList(databaseId));
		model.addAttribute("tableColumnRegisterForm", form);
		DataTypeResolver dataTypeResolver = resolverFactory.getResolver(dbms);
		model.addAttribute("dataTypeResolver", dataTypeResolver);
	}

}
