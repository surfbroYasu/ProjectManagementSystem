package com.example.projectmanagement.modules.databases.services.application.context.column;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.constance.ModelAttributes;
import com.example.projectmanagement.modules.databases.datastructure.dto.TableColumnJoinedDto;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.repository.DbColumnJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.DBContextHelperService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DbColumnContextService{

	@Autowired
	private DBContextHelperService helper;
	
	@Autowired
	private DbColumnJpaRepository jpaRepo;
	
	@Autowired
	private DataTypeResolverFactory resolverFactory;
	
	@Autowired
	private DBInfoMapper mapper;


	
	public List<TableColumnJoinedDto> getColumnDtoListByTableId(Long tableId) {
	    return mapper.getColumnsByTableIds(List.of(tableId));
	}

	public List<TableColumnJoinedDto> getTableColumnJoinedDtoListByTableIdList(List<Long> tableIds) {
		return mapper.getColumnsByTableIds(tableIds);
	}

	
	public List<String> extractColumnNames(List<TableColumnJoinedDto> columnList){
		return columnList.stream()
				.map(TableColumnJoinedDto::getColumnName)
				.collect(Collectors.toList());
	}

	public void setupColumnList(Model model, List<TableColumnJoinedDto> dtos) {
		model.addAttribute(ModelAttributes.COLUMN_LIST, dtos);
	}
	

	public List<TableColumnEntity> getFKList(int dbId) {
		return mapper.getFKList(dbId);
	}
	
	/**
	 * テーブルカラム登録フォームとデータ型解決オブジェクトをModelに追加する。
	 * 
	 * Modelに追加されるキー（thymeleaf対応）：
	 * - "tableColumnRegisterForm" : TableColumnRegisterForm
	 * - "dataTypeResolver" : DataTypeResolver
	 *
	 * @param model Modelオブジェクト
	 * @param databaseId 外部キー選択肢取得用のデータベースID
	 * @param dbms 対象DBMSの名称（例：mariadb, postgresql）
	 */
	public void prepareColumnForm(Model model, Integer databaseId, String dbms) {
		TableColumnRegisterForm form = new TableColumnRegisterForm();
		form.setForignOptions(getFKList(databaseId));
		model.addAttribute(ModelAttributes.COLUMN_REGISTER_FORM, form);
		DataTypeResolver dataTypeResolver = resolverFactory.getResolver(dbms);
		model.addAttribute(ModelAttributes.DATA_TYPE_RESOLVER, dataTypeResolver);
	}
	

//	private TableColumnEntity setResolvedColumn(TableColumnEntity column, String dbms) {
//		DataTypeResolver resolver = resolverFactory.getResolver(dbms);
//		column = resolver.adjustDataTypeParam(column);
//		return column;
//	}

}
