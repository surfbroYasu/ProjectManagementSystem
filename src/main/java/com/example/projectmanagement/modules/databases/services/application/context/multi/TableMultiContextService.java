package com.example.projectmanagement.modules.databases.services.application.context.multi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.constance.ModelAttributes;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.repository.DbTableJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.DBContextHelperService;
import com.example.projectmanagement.modules.databases.services.application.context.database.DbInfoContextService;

@Service
public class TableMultiContextService {
	@Autowired
	private DBContextHelperService helper;
	@Autowired
	private DbTableJpaRepository tableJpaRepo;
	@Autowired
	private DbInfoContextService dbContext;
	/**
	 * プロジェクトに関連するDBすべてのテーブル情報をModelに追加する。
	 *
	 * Model属性:
	 * - "tableInfoMap" : Map<Integer, List<TableInfoDtoRecord>>
	 *
	 * @param model Modelオブジェクト
	 * @param projectId プロジェクトID
	 */
	public void setupAllDatabaseTables(Model model, List<DBInfoDtoRecord> dtoList) {
		List<Integer> dbIds = dbContext.extractDbIdsFromDto(dtoList);
		model.addAttribute(ModelAttributes.TABLE_INFO_MAP,
				helper.convertToTableDtoMap(tableJpaRepo.findAllByDbInfoIdIn(dbIds)));
	}	
}
