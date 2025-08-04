package com.example.projectmanagement.modules.databases.services.application.context.multi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.constance.ModelAttributes;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableColumnJoinedDto;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.services.application.context.DBContextHelperService;
import com.example.projectmanagement.modules.databases.services.application.context.column.DbColumnContextService;
import com.example.projectmanagement.modules.databases.services.application.context.table.DbTableContextService;

@Service
public class ColumnMultiContextService {

	@Autowired
	private DBContextHelperService helper;

	@Autowired
	private DbTableContextService tableContextService;
	@Autowired
	private DbColumnContextService columContextService;

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
	public void setupDbDetailWithTablesAndColums(Model model, List<TableInfoDtoRecord> dtoTables,
			List<TableColumnJoinedDto> columns) {

		model.addAttribute(ModelAttributes.TABLE_LIST, dtoTables);

		Map<Long, List<TableColumnJoinedDto>> columnsWithRelatedTableId = helper.groupTableColumnJoinedDtoByTableId(columns);
		model.addAttribute(ModelAttributes.COLUMN_MAP, columnsWithRelatedTableId);

	}

	/**
	 * テーブル詳細とカラム情報をModelに登録する（ページ表示用）。
	 * 
	 * tableContext.setupTableDetail + columnContext.setupColumnList
	 *
	 * @param model Thymeleaf用Model
	 * @param tableId テーブルID
	 */
	public void setTableDetailWithColumns(Model model, TableInfoDtoRecord table, List<TableColumnJoinedDto> columns) {
		tableContextService.setupTableDetail(model, table);
		columContextService.setupColumnList(model, columns);
	}

}
