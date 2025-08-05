package com.example.projectmanagement.modules.databases.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.form.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.repository.DbInfoJpaRepository;

@Service
public class DatabaseRepositoryService {


	@Autowired
	private DbInfoJpaRepository jpaRepo;
	
	public void deleteByIdIfExists(int id) {
		if (jpaRepo.existsById(id)) {
			jpaRepo.deleteById(id);
		}
	}
	
	public void saveByAction(String action, DBInfoRegisterForm form) {
		DBInfoEntity entity = switch (action) {
		case "add" -> {
			DBInfoEntity newEntity = new DBInfoEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			DBInfoEntity existing = jpaRepo.findById(form.getId())
					.orElseThrow(() -> new IllegalArgumentException("Database not found"));
			BeanUtils.copyProperties(form, existing);
			yield existing;
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		};

		jpaRepo.save(entity);
	}
		
}
