package com.example.projectmanagement.modules.databases.services.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DbTableColumnService {

	@Autowired
	private DBInfoMapper mapper;
	
	@Autowired
	private DataTypeResolverFactory DTRfactory;
	/*
	 * ----COLUMNS----
	 */
	public void insertColumn(TableColumn domain, String dbms) {
		
		DataTypeResolver resolver = DTRfactory.getResolver(dbms);
		resolver.adjustDataTypeParam(domain);
		mapper.insertNewColumn(domain);
	}

	public void updateColumn(TableColumn domain, String dbms) {
		DataTypeResolver resolver = DTRfactory.getResolver(dbms);
		resolver.adjustDataTypeParam(domain);
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

}
