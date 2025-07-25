package com.example.projectmanagement.modules.databases.services.application.context;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.dto.ColumnDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.DBInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableInfoDtoRecord;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;

@Service
public class DBContextHelperService {

	
	public Map<Integer, List<TableInfoDtoRecord>> convertToTableDtoMap(List<TableInfoEntity> tableInfos) {
		return tableInfos.stream()
				.map(this::convertTableInfoToDto)
				.collect(Collectors.groupingBy(TableInfoDtoRecord::dbInfoId));
	}
	
	public Map<Long, List<ColumnDtoRecord>> convertEntityToColumnDtoMap(List<TableColumnEntity> columns) {
		return columns.stream()
				.map(this::columnInfoToDto)
				.collect(Collectors.groupingBy(ColumnDtoRecord::tableInfoId));
	}
	
	public Map<Long, List<ColumnDtoRecord>> groupColumnDtoByTableId(List<ColumnDtoRecord> columns) {
		return columns.stream()
			.collect(Collectors.groupingBy(ColumnDtoRecord::tableInfoId));
	}


	public DBInfoDtoRecord convertDbInfoToDto(DBInfoEntity entity) {
		return new DBInfoDtoRecord(
				entity.getId(),
				entity.getProjectId(),
				entity.getDbName(),
				entity.getDbms());
	}

	public TableInfoDtoRecord convertTableInfoToDto(TableInfoEntity entity) {
		return new TableInfoDtoRecord(
				entity.getId(),
				entity.getDbInfoId(),
				entity.getTableName(),
				entity.getTableAlias());
	}

	public ColumnDtoRecord columnInfoToDto(TableColumnEntity entity) {
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
