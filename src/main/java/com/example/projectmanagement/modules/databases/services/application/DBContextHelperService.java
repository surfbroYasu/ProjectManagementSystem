package com.example.projectmanagement.modules.databases.services.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.dto.ColumnDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;

@Service
public class DBContextHelperService {

	
	Map<Integer, List<TableInfoDtoRecord>> convertToTableDtoMap(List<TableInfo> tableInfos) {
		return tableInfos.stream()
				.map(this::tableInfoToDto)
				.collect(Collectors.groupingBy(TableInfoDtoRecord::dbInfoId));
	}
	
	Map<Integer, List<ColumnDtoRecord>> convertToColumnDtoMap(List<TableColumn> columns) {
		return columns.stream()
				.map(this::columnInfoToDto)
				.collect(Collectors.groupingBy(ColumnDtoRecord::tableInfoId));
	}

	DBInfoDtoRecord dbInfoToDto(DBInfo entity) {
		return new DBInfoDtoRecord(
				entity.getId(),
				entity.getProjectId(),
				entity.getDbName(),
				entity.getDbms());
	}

	TableInfoDtoRecord tableInfoToDto(TableInfo entity) {
		return new TableInfoDtoRecord(
				entity.getId(),
				entity.getDbInfoId(),
				entity.getTableName(),
				entity.getTableAlias());
	}

	ColumnDtoRecord columnInfoToDto(TableColumn entity) {
		return new ColumnDtoRecord(
				entity.getId(),
				entity.getTableInfoId(),
				entity.getColumnName(),
				entity.getAlias(),
				entity.getDataType(),
				entity.getDataTypeParam(),
				entity.getIsPrimary(),
				entity.getIsUnique(),
				entity.getIsForign(),
				entity.getIsNullable(),
				entity.getIsAutoIncrement(),
				entity.getDefaultValue(),
				entity.getForignId(),
				entity.getCheckConstraint(),
				entity.getComment(),
				entity.getOnDelete(),
				entity.getOnUpdate(),
				entity.getTableName(),
				entity.getRefTableName(),
				entity.getRefColumnName());
	}
}
