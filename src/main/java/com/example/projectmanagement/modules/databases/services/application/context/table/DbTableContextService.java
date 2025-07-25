package com.example.projectmanagement.modules.databases.services.application.context.table;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.repository.DbTableJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.DBContextHelperService;
import com.example.projectmanagement.modules.databases.services.application.context.ModelAttributes;

@Service
public class DbTableContextService {
	@Autowired
	private DbTableJpaRepository tableJpaRepo;
	@Autowired
	private DBContextHelperService helper;

	/**
	 * テーブルIDを指定して DTO を取得する（存在すれば Optional で返す）
	 *
	 * @param tableId テーブルID
	 * @return Optional<TableInfoDtoRecord>
	 */
	public TableInfoDtoRecord getTableDtoById(Long tableId) {
		return tableJpaRepo.findById(tableId)
				.map(helper::convertTableInfoToDto)
				.orElseThrow(() -> new IllegalArgumentException("テーブルが存在しません: id=" + tableId));
	}

	/**
	 * 指定されたデータベースIDに紐づくテーブル情報をDTO形式で取得する。
	 * 該当テーブルが存在しない場合は空リストを返す。
	 *
	 * @param databaseId データベースID
	 * @return テーブルDTOのリスト（空リストを含む）
	 */
	public List<TableInfoDtoRecord> getTableDtoListByDbId(Integer databaseId) {
		return tableJpaRepo.findAllByDbInfoId(databaseId).stream()
				.map(helper::convertTableInfoToDto)
				.collect(Collectors.toList());
	}

	/**
	 * テーブル情報のDTOを Model に登録する。
	 *
	 * Model属性：
	 * - "table" : TableInfoDtoRecord
	 * - "tableNotFound" : true（見つからない場合）
	 *
	 * @param model Modelオブジェクト
	 * @param tableId テーブルID
	 */
	public void setupTableDetail(Model model, TableInfoDtoRecord dto) {
		model.addAttribute(ModelAttributes.TABLE, dto);
	}
	
	public List<Long> extractTableIds(List<TableInfoDtoRecord> tableDtoList){
		return tableDtoList.stream()
				.map(TableInfoDtoRecord::id)
				.collect(Collectors.toList());
	}

}
