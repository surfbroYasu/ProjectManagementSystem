package com.example.projectmanagement.modules.databases.services.application.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.constance.ModelAttributes;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableColumnJoinedDto;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;
import com.example.projectmanagement.modules.databases.repository.DbColumnJpaRepository;
import com.example.projectmanagement.modules.databases.repository.DbTableJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.column.DbColumnContextService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlSyntaxGenerator;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

/**
 * SQL文を生成し、Thymeleafテンプレートで使用するModelに追加するコンテキストサービスクラス。
 * 主に指定されたDBMSとテーブル情報をもとに、
 * CREATE, DROP, INSERT, UPDATE, DELETE のテンプレートSQLを生成し、
 * それらを画面描画用のModelに格納する。
 * 
 * <p>依存コンポーネント:</p>
 * <ul>
 *   <li>{@link DbColumnContextService} - 対象テーブルに紐づくカラム情報を取得</li>
 *   <li>{@link SqlGeneratorFactory} - 指定DBMSに応じたSQL生成を提供</li>
 * </ul>
 * 
 * <p>Modelに追加される属性名:</p>
 * <ul>
 *   <li>createTableSQL</li>
 *   <li>dropTableSQL</li>
 *   <li>insertTemplateSQL</li>
 *   <li>updateTemplateSQL</li>
 *   <li>deleteTemplateSQL</li>
 * </ul>
 * 
 * @author yasufumimisono
 */
@Service
public class SqlContextService{

	@Autowired
	private DbColumnContextService columnContext;
	@Autowired
	private SqlGeneratorFactory sqlFactory;
	@Autowired
	private DbColumnJpaRepository colJpaRepo;
	@Autowired
	private DbTableJpaRepository tableJpaRepo;
	
	@Autowired
	private DBInfoMapper mapper;


	/**
	 * テーブルIDに該当するTableInfoEntityを取得し、存在しない場合は
	 * エラーフラグとメッセージをModelに追加し、nullを返す。
	 *
	 * @param model Modelオブジェクト
	 * @param tableId テーブルID
	 * @return TableInfoEntity（存在すれば）、存在しない場合はnull
	 */
	private TableInfoEntity getTableOrSetError(Model model, Long tableId) {
		return tableJpaRepo.findById(tableId)
				.orElseGet(() -> {
					model.addAttribute("sqlGenerationFailed", true);
					model.addAttribute("errorMessage", "テーブルが存在しません: id=" + tableId);
					return null;
				});
	}

	public void setAllSQLtoModel(Model model, String dbms, Long targetTableId) {

		List<TableColumnJoinedDto> columnList = mapper.getColumnsByTableIds(List.of(targetTableId));
		TableInfoEntity table = getTableOrSetError(model, targetTableId);
		if (table == null)
			return;

		List<String> columnNames = columnContext.extractColumnNames(columnList);
		String tableName = table.getTableName();

		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(dbms);

		setCreate(model, generateCreate(sqlGen, table, columnList));
		setDrop(model, generateDrop(sqlGen, tableName));
		setInsertTemplate(model, generateInsertTemplate(sqlGen, table, columnNames));
		setUpdateTemplate(model, generateUpdateTemplate(sqlGen, table, columnNames));

		if (!columnNames.isEmpty()) {
			setDeleteTemplate(model, generateDeleteTemplate(sqlGen, tableName, columnNames));
		} else {
			setDeleteTemplate(model, "-- No columns available to generate DELETE SQL");
		}
	}

	/**
	 * 指定されたテーブル情報をもとに、DDL（CREATE, DROP）文のみを生成し、
	 * Modelに登録する。
	 *
	 * <p>Model属性名:</p>
	 * <ul>
	 *   <li>createTableSQL</li>
	 *   <li>dropTableSQL</li>
	 * </ul>
	 *
	 * @param model Thymeleafで使用するModel
	 * @param dbms 使用するDBMS名
	 * @param targetTable 対象のテーブル情報エンティティ
	 */
	public void setDDLtoModel(Model model, String dbms, Long targetTableId) {

		List<TableColumnJoinedDto> columnList = mapper.getColumnsByTableIds(List.of(targetTableId));
		TableInfoEntity table = getTableOrSetError(model, targetTableId);
		if (table == null)
			return;

		String tableName = table.getTableName();

		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(dbms);

		setCreate(model, generateCreate(sqlGen, table, columnList));
		setDrop(model, generateDrop(sqlGen, tableName));
	}

	/**
	 * 指定されたテーブル情報をもとに、DML（INSERT, UPDATE, DELETE）文のみを生成し、
	 * Modelに登録する。
	 *
	 * <p>Model属性名:</p>
	 * <ul>
	 *   <li>insertTemplateSQL</li>
	 *   <li>updateTemplateSQL</li>
	 *   <li>deleteTemplateSQL</li>
	 * </ul>
	 *
	 * @param model Thymeleafで使用するModel
	 * @param dbms 使用するDBMS名
	 * @param targetTable 対象のテーブル情報エンティティ
	 */
	public void setDMLtoModel(Model model, String dbms, Long targetTableId) {

		List<TableColumnJoinedDto> columnList = mapper.getColumnsByTableIds(List.of(targetTableId));
		TableInfoEntity table = getTableOrSetError(model, targetTableId);
		if (table == null) return;

		List<String> columnNames = columnContext.extractColumnNames(columnList);
		String tableName = table.getTableName();

		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(dbms);

		setInsertTemplate(model, generateInsertTemplate(sqlGen, table, columnNames));
		setUpdateTemplate(model, generateUpdateTemplate(sqlGen, table, columnNames));

		if (!columnNames.isEmpty()) {
			setDeleteTemplate(model, generateDeleteTemplate(sqlGen, tableName, columnNames));
		} else {
			setDeleteTemplate(model, "-- No columns available to generate DELETE SQL");
		}
	}

	private String generateCreate(SqlSyntaxGenerator sqlGen,
			TableInfoEntity targetTable,
			List<TableColumnJoinedDto> columnList) {
		return sqlGen.createTable(targetTable, columnList);
	}

	private String generateDrop(SqlSyntaxGenerator sqlGen, String tableName) {
		return sqlGen.dropTable(tableName);
	}

	private String generateInsertTemplate(SqlSyntaxGenerator sqlGen, TableInfoEntity targetTable,
			List<String> columnNames) {
		return sqlGen.insertTemplate(targetTable.getTableName(), columnNames);
	}

	private String generateUpdateTemplate(SqlSyntaxGenerator sqlGen, TableInfoEntity targetTable,
			List<String> columnNames) {
		return sqlGen.updateTemplate(targetTable.getTableName(), columnNames, "id");
	}

	private String generateDeleteTemplate(SqlSyntaxGenerator sqlGen, String tableName, List<String> columnNames) {
		return sqlGen.deleteTemplate(tableName, columnNames.getFirst());

	}

	private void setCreate(Model model, String sql) {
		model.addAttribute(ModelAttributes.CREATE_SQL, sql);
	}

	private void setDrop(Model model, String sql) {
		model.addAttribute(ModelAttributes.DROP_SQL, sql);
	}

	private void setInsertTemplate(Model model, String sql) {
		model.addAttribute(ModelAttributes.INSERT_SQL, sql);
	}

	private void setUpdateTemplate(Model model, String sql) {
		model.addAttribute(ModelAttributes.UPDATE_SQL, sql);
	}

	private void setDeleteTemplate(Model model, String sql) {
		model.addAttribute(ModelAttributes.DELETE_SQL, sql);
	}

}
