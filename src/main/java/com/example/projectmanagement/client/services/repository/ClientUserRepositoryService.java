package com.example.projectmanagement.client.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.client.datastructures.entities.ClientUserInfoEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientUserInfoForm;
import com.example.projectmanagement.client.repository.ClientUserJpaRepository;

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
	private ClientUserJpaRepository jpaRepo;

	
	/**
	 * @param action
	 * @param form
	 */
	public void saveByAction(String action, ClientUserInfoForm form) {
		ClientUserInfoEntity entity = switch (action) {
		case "add" -> {
			ClientUserInfoEntity newEntity = new ClientUserInfoEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			ClientUserInfoEntity existing = jpaRepo.findById(form.getId())
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
