package com.example.projectmanagement.modules.databases.services.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DatabaseService {

	@Autowired
	private DBInfoMapper mapper;

	/*
	 * ----DB INFO----
	 */

	public void insertDatabase(DBInfo entity) {
		mapper.insertNewDatabase(entity);
	}

	public void updateDatabase(DBInfo entity) {
		mapper.updateDatabase(entity);
	}

	public void deleteDatabase(int dbId) {
		mapper.deleteDatabase(dbId);
	}

	public List<DBInfo> getAll(int projectId) {
		return mapper.getDBInfoByProject(projectId);
	}

	public DBInfo getDBInfoByDBId(int dbId) {
		return mapper.getDBInfoByDBId(dbId);
	}

}
