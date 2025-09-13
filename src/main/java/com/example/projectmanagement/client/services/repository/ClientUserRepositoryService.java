package com.example.projectmanagement.client.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.client.datastructures.entities.ProjectClientPersonnelEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientUserInfoForm;
import com.example.projectmanagement.client.repository.ProjectClientPersonnelJpaRepository;

/**
 * このクラスはデータの永続化にかかわるビジネスロジックを扱うクラスとして使用
 * 
 * 
 * @author yasufumimisono
 *
 */
@Service
public class ClientUserRepositoryService {

	@Autowired
	private ProjectClientPersonnelJpaRepository jpaRepo;

	
	/**
	 * @param action
	 * @param form
	 */
	public void saveByAction(String action, ClientUserInfoForm form) {
		ProjectClientPersonnelEntity entity = switch (action) {
		case "add" -> {
			ProjectClientPersonnelEntity newEntity = new ProjectClientPersonnelEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			ProjectClientPersonnelEntity existing = jpaRepo.findById(form.getId())
					.orElseThrow(() -> new IllegalArgumentException("User not found"));
			BeanUtils.copyProperties(form, existing);
			yield existing;
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		};

		jpaRepo.save(entity);
	}

	public void deleteByIdIfExists(int id) {
		if (jpaRepo.existsById(id)) {
			jpaRepo.deleteById(id);
		}
	}

	/*
	 * 
	 * TODO
	 * クライアントユーザー情報をユーザーに紐づけする
	 * 後で実装
	 */

}
