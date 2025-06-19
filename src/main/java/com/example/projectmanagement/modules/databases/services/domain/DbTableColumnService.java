package com.example.projectmanagement.modules.databases.services.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DbTableColumnService {

	@Autowired
	private DBInfoMapper mapper;
	/*
	 * ----COLUMNS----
	 */
	public void insertColumn(TableColumn domain) {
		intParamChecker(domain);
		mapper.insertNewColumn(domain);
	}

	public void updateColumn(TableColumn domain) {
		intParamChecker(domain);
		mapper.updateColumn(domain);
	}

	public void deleteColumn(int columnId) {
		mapper.deleteColumn(columnId);
	}

	public List<TableColumn> getFKList(int dbId) {
		return mapper.getFKList(dbId);
	}

	public List<TableColumn> getTableColumns(List<Integer> tableIds) {
		return mapper.getColumnsByTableIds(tableIds);
	}

	public Map<Integer, List<TableColumn>> getRelatedColumns(List<TableInfo> tables) {
		List<Integer> tableIds = tables.stream()
				.map(TableInfo::getId)
				.collect(Collectors.toList());

		List<TableColumn> relatedColumns = mapper.getColumnsByTableIds(tableIds);

		return relatedColumns.stream().collect(Collectors.groupingBy(TableColumn::getTableInfoId));
	}

	/**
	 * 
	 * 数値型の引数の入力チェックを行う
	 * 引数がない場合のデフォルト値を11にセットする。
	 * @param domain
	 * @return　ユーザー入力数値・またはデフォルト11
	 */
	private TableColumn intParamChecker(TableColumn domain) {
		switch (domain.getDataType()) {
		case "INT":			
			if (domain.getDataTypeParam().isBlank()) {
				domain.setDataTypeParam("11");
			}
			break;

		case "TINYINT":
			if (domain.getDataTypeParam().isBlank()) {
				domain.setDataTypeParam("1");
			}
			break;

		default:
			break;
		}
		return domain;
	}
}
