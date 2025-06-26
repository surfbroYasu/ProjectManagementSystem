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
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlSyntaxGenerator;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableService;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;

@Component
public class DBViewContextService extends ProjectViewContextService {

	@Autowired
	private DatabaseService databaseService;
	@Autowired
	private DbTableService tableService;
	@Autowired
	private DbTableColumnService columnService;
	@Autowired
	private DataTypeResolverFactory resolverFactory;
	@Autowired
	private SqlGeneratorFactory sqlFactory;
	@Autowired
	private DBContextHelperService helper;

	/**
	 * プロジェクトに関連する全てのDBとそのテーブルを取得し、Modelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "dbList" : List<DBInfoDtoRecord>
	 * - "tableInfoMap" : Map<Integer, List<TableInfoDtoRecord>>
	 *
	 * @param model Modelオブジェクト
	 * @param dbs プロジェクトに紐づくDBのリスト
	 */
	public void setAllDatabaseTablesContext(Model model, List<DBInfo> dbs) {
		List<DBInfoDtoRecord> dbList = new ArrayList<>();
		for (DBInfo each : dbs) {
			DBInfoDtoRecord eachDto = helper.dbInfoToDto(each);
			dbList.add(eachDto);
		}
		model.addAttribute("dbList", dbList);

		List<Integer> ids = dbs.stream()
				.map(DBInfo::getId)
				.collect(Collectors.toList());
		Map<Integer, List<TableInfoDtoRecord>> tablesWithRelatedDBId = helper.convertToTableDtoMap(
				tableService.getTableInfoByDbIds(ids));
		model.addAttribute("tableInfoMap", tablesWithRelatedDBId);
	}

	/**
	 * データベースIDをもとに、DB情報とプロジェクト情報を取得してModelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "project" : ProjectDtoRecord
	 * - "db" : DBInfoDtoRecord
	 *
	 * @param model Modelオブジェクト
	 * @param databaseId 対象のデータベースID
	 * @return DBInfoエンティティ
	 */
	public DBInfo setDatabaseContext(Model model, Integer databaseId) {
		DBInfo db = databaseService.getDBInfoByDBId(databaseId);
		DBInfoDtoRecord dbDto = helper.dbInfoToDto(db);

		setProjectToModel(model, db.getProjectId());
		model.addAttribute("db", dbDto);
		return db;
	}

	/**
	 * データベースIDをもとに、関連するテーブル情報とカラム情報を取得し、Modelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "tableList" : List<TableInfoDtoRecord>
	 * - "columnMap" : Map<Integer, List<ColumnDtoRecord>>
	 *
	 * @param model Modelオブジェクト
	 * @param databaseId 対象のデータベースID
	 */
	public void setDatabaseTablesContext(Model model, Integer databaseId) {

		List<TableInfo> relatedTables = tableService.getTableInfoByDbIds(List.of(databaseId));
		List<TableInfoDtoRecord> dtoTables = new ArrayList<>();
		for (TableInfo each : relatedTables) {
			dtoTables.add(helper.tableInfoToDto(each));
		}
		model.addAttribute("tableList", dtoTables);

		List<Integer> ids = relatedTables.stream()
				.map(TableInfo::getId)
				.collect(Collectors.toList());

		Map<Integer, List<ColumnDtoRecord>> columnsWithRelatedTableId = helper.convertToColumnDtoMap(
				columnService.getTableColumns(ids));
		model.addAttribute("columnMap", columnsWithRelatedTableId);

	}

	/**
	 * 単一のテーブル情報およびそのカラム情報をModelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "table" : TableInfoDtoRecord
	 * 
	 * @param model Modelオブジェクト
	 * @param tableId テーブルID
	 * @return TableInfoエンティティ
	 */
	public TableInfo setSingleTableContext(Model model, Integer tableId) {
		TableInfo table = tableService.getTableByTableId(tableId);

		List<TableColumn> columnList = columnService.getTableColumns(List.of(table.getId()));
		TableInfoDtoRecord tableDto = helper.tableInfoToDto(table);
		model.addAttribute("table", tableDto);

		List<ColumnDtoRecord> columnListDto = new ArrayList<>();
		for (TableColumn each : columnList) {
			columnListDto.add(helper.columnInfoToDto(each));
		}
		model.addAttribute("columnList", columnListDto);

		return table;
	}

	/**
	 * テーブルカラム登録フォームとデータ型解決オブジェクトをModelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "tableColumnRegisterForm" : TableColumnRegisterForm
	 * - "dataTypeResolver" : DataTypeResolver
	 *
	 * @param model Modelオブジェクト
	 * @param databaseId 外部キー選択肢取得用のデータベースID
	 * @param dbms 対象DBMSの名称（例：mariadb, postgresql）
	 */
	public void prepareColumnFormForModel(Model model, Integer databaseId, String dbms) {
		TableColumnRegisterForm form = new TableColumnRegisterForm();
		form.setForignOptions(columnService.getFKList(databaseId));
		model.addAttribute("tableColumnRegisterForm", form);
		DataTypeResolver dataTypeResolver = resolverFactory.getResolver(dbms);
		model.addAttribute("dataTypeResolver", dataTypeResolver);
	}

	/**
	 * TableInfoをもとにSQLを生成します。
	 * @param model
	 * @param dbms
	 * @param targetTable
	 */
	public void setSQLtoModel(Model model, String dbms, TableInfo targetTable) {

		List<TableColumn> columnList = columnService.getTableColumns(List.of(targetTable.getId()));

		List<String> columnNames = columnList.stream()
				.map(TableColumn::getColumnName)
				.collect(Collectors.toList());

		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(dbms);
		model.addAttribute("createTableSQL", sqlGen.createTable(targetTable, columnList));
		model.addAttribute("dropTableSQL", sqlGen.dropTable(targetTable.getTableName()));
		model.addAttribute("insertTemplateSQL", sqlGen.insertTemplate(targetTable.getTableName(), columnNames));
		model.addAttribute("updateTemplateSQL", sqlGen.updateTemplate(targetTable.getTableName(), columnNames, "id"));
		model.addAttribute("deleteTemplateSQL", sqlGen.deleteTemplate(targetTable.getTableName(), columnNames.getFirst()));
	}
}
