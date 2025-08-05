package com.example.projectmanagement.client.services.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.client.datastructures.entities.ClientEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientForm;
import com.example.projectmanagement.client.repository.ClientJpaRepository;

@Service
public class ClientRepositoryService {
	
	@Autowired
	private ClientJpaRepository jpaRepo;
	
	/**
	 * @param action
	 * @param form
	 */
	public void saveByAction(String action, ClientForm form) {
		ClientEntity entity = switch (action) {
		case "add" -> {
			ClientEntity newEntity = new ClientEntity();
			BeanUtils.copyProperties(form, newEntity);
			yield newEntity;
		}
		case "edit" -> {
			ClientEntity existing = jpaRepo.findById(form.getId())
					.orElseThrow(() -> new IllegalArgumentException("Client not found"));
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
	
}
