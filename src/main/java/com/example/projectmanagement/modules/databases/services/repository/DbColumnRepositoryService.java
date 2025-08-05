package com.example.projectmanagement.modules.databases.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.repository.DbColumnJpaRepository;
import com.example.projectmanagement.modules.databases.services.application.context.database.DbInfoContextService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.persistence.modules.databases.DBInfoMapper;

@Service
public class DbColumnRepositoryService {
	
	@Autowired
	private DataTypeResolverFactory DTRfactory;
	
	@Autowired
	private DbInfoContextService dbContext;
	
	@Autowired
	private DBInfoMapper mapper;
	
	@Autowired
	private DbColumnJpaRepository jpaRepo;
	
	private TableColumnEntity setResolvedColumn(TableColumnEntity column, String dbms) {
		DataTypeResolver resolver = DTRfactory.getResolver(dbms);
		column = resolver.adjustDataTypeParam(column);
		return column;
	}
	
	
	public void saveByAction(String action, Integer databaseId, TableColumnRegisterForm form) {
		TableColumnEntity entity = switch (action) {
		case "add" -> {
			TableColumnEntity newEntity = new TableColumnEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			TableColumnEntity existing = jpaRepo.findById(form.getId())
					.orElseThrow(() -> new IllegalArgumentException("Column not found"));
			BeanUtils.copyProperties(form, existing);
			yield existing;
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		};
		
		setResolvedColumn(entity, dbContext.findDbms(databaseId));
		jpaRepo.save(entity);
	}

	public void deleteByIdIfExists(long id) {
		if (jpaRepo.existsById(id)) {
			jpaRepo.deleteById(id);
		}
	}


}
