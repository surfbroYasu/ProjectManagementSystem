package com.example.projectmanagement.modules.databases.services.application.context.database;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.repository.DbInfoJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.DBContextHelperService;
import com.example.projectmanagement.modules.databases.services.application.context.ModelAttributes;

@Service
public class DbInfoContextService{

	@Autowired
	private DbInfoJpaRepository jpaRepo;	

	@Autowired
	private DBContextHelperService helper;



	/**
	 * プロジェクトIDに基づき、DBリストを取得しModelに追加する。
	 *
	 * @param model Modelオブジェクト
	 * @param projectId プロジェクトID
	 */
	public void setupDbList(Model model, List<DBInfoDtoRecord> dtoList) {
//		List<DBInfoDtoRecord> dtoList = getDbInfoDtoByProjectId(projectId);
		model.addAttribute(ModelAttributes.DB_LIST, dtoList);
	}

	/**
	 * データベースIDをもとに、DB情報と「DBが存在しない」フラグをModelに追加する。
	 *
	 * Model属性:
	 * - "dbList" : List<DBInfoDtoRecord>
	 * - "dbNotFound" : Boolean
	 */
	public void setupDbInfo(Model model, DBInfoDtoRecord dbDto) {
		model.addAttribute(ModelAttributes.DB, dbDto);
//		model.addAttribute(ModelAttributes.DB_NOT_FOUND, dtoList.isEmpty());
	}

	
	/**
	 * DBエンティティからDBMS種別を取得する。
	 *
	 * @param databaseId 対象のDB ID
	 * @return DBMS種別
	 */
	public String findDbms(int databaseId) {
		return jpaRepo.findById(databaseId)
				.map(DBInfoEntity::getDbms)
				.orElseThrow(() -> new IllegalArgumentException("DBMSが存在しません: id=" + databaseId));
	}

	/**
	 * 単一のDB IDに対応するDTOを返す（存在しない場合は空リスト）。
	 */
	public DBInfoDtoRecord getDbInfoDtoById(Integer databaseId) {
		return jpaRepo.findById(databaseId)
				.map(helper::convertDbInfoToDto)
				.orElseThrow(() -> new IllegalArgumentException("DBが存在しません: id=" + databaseId));
	}
	
	/**
	 * projectIdに対応する複数のDTO（DBInfo）を返す（存在しない場合は空リスト）。
	 */
	public List<DBInfoDtoRecord> getDbInfoDtoByProjectId(Integer projectId) {
		return jpaRepo.findAllByProjectId(projectId).stream()
				.map(helper::convertDbInfoToDto)
				.collect(Collectors.toList());
	}
	
	/**
	 * projectIdに対応する複数のDBIdを返す（存在しない場合は空リスト）。
	 */
//	public List<Integer> getAllDbIdsByProjectId(Integer projectId) {
//		return jpaRepo.findAllByProjectId(projectId).stream()
//				.map(DBInfoEntity::getId)
//				.collect(Collectors.toList());
//	}
	
	public List<Integer> extractDbIdsFromDto(List<DBInfoDtoRecord> dto){
		return dto.stream().map(DBInfoDtoRecord:: id)
				.collect(Collectors.toList());
	}

}
