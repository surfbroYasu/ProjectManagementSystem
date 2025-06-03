package com.example.projectmanagement.modules.databases.services.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.persistence.DBInfoMapper;

@Service
public class DbTableService {
	@Autowired
	private DBInfoMapper mapper;

	public void insertTable(TableInfo domain) {
		mapper.insertNewTable(domain);
	}

	public void updateTable(TableInfo domain) {
		mapper.updateTable(domain);
	}

	public void deleteTable(int tableId) {
		mapper.deleteTable(tableId);
	}

	public TableInfo getTableByTableId(int tableId) {
		return mapper.getTableByTableId(tableId);
	}

	public List<TableInfo> getTableInfoByDbIds(List<Integer> dbInfoIds) {
		return mapper.getTableInfoByDbIds(dbInfoIds);
	}

}
