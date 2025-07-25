package com.example.projectmanagement.modules.databases.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.repository.DbTableJpaRepository;

@Service
public class DbTableRepositoryService {
	
	@Autowired
	private DbTableJpaRepository jpaRepo;
	
	public void saveByAction(String action, TableInfoRegisterForm form) {
		TableInfoEntity entity = switch (action) {
		case "add" -> {
			TableInfoEntity newEntity = new TableInfoEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			TableInfoEntity existing = jpaRepo.findById(form.getId())
					.orElseThrow(() -> new IllegalArgumentException("Table not found"));
			BeanUtils.copyProperties(form, existing);
			yield existing;
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		};

		jpaRepo.save(entity);
	}
	
	public void deleteByIdIfExists(long id) {
		if (jpaRepo.existsById(id)) {
			jpaRepo.deleteById(id);
		}
	}
}
