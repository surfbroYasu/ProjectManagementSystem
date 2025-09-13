package com.example.projectmanagement.client.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectmanagement.client.datastructures.entities.ClientBasicInfoEntity;
import com.example.projectmanagement.client.datastructures.entities.ProjectClientEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientForm;
import com.example.projectmanagement.client.repository.ClientInfoJpaRepository;
import com.example.projectmanagement.client.repository.ProjectClientJpaRepository;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;
import com.example.projectmanagement.organizations.datastructure.entity.OrganizationEntity;

@Service
public class ClientRepositoryService {

	@Autowired
	private ClientInfoJpaRepository clientRepo;

	@Autowired
	private ProjectClientJpaRepository projectClientRepo;

	/**
	 * @param action
	 * @param form
	 */
	@Transactional
	public void saveByAction(String action, ClientForm form) {

		switch (action) {
		case "add" -> {
			ClientBasicInfoEntity saved = clientRepo.save(setClientBasicInfo(form));

			projectClientRepo.save(setProjectClient(form, saved));
		}
		case "edit" -> {
			ClientBasicInfoEntity saved = setClientBasicInfo(form);

			saved.setId(form.getId()); //ClientBasicInfoEntity　のIdをセットして編集保存にする＜＜変更禁止＞＞
			clientRepo.save(saved);

			ProjectClientEntity oldProjectClient = projectClientRepo.findByProjectId(form.getProjectId()).orElseThrow();
			ProjectClientEntity newProjectClient =setProjectClient(form, saved);
			newProjectClient.setId(oldProjectClient.getId()); //ProjectClientEntity　のIdをセットして編集保存にする＜＜変更禁止＞＞
			projectClientRepo.save(newProjectClient);
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}
		;
	}

	private ClientBasicInfoEntity setClientBasicInfo(ClientForm form) {

		ClientBasicInfoEntity clientInfo = new ClientBasicInfoEntity();
		clientInfo.setName(form.getName());
		clientInfo.setPhone(form.getPhone());

		if (!form.getOrganizationId().equals(null)) {
			OrganizationEntity newOrg = new OrganizationEntity();
			newOrg.setId(form.getOrganizationId());
			clientInfo.setOrganization(newOrg);
		}

		return clientInfo;
	}

	private ProjectClientEntity setProjectClient(ClientForm form, ClientBasicInfoEntity saved) {
		ProjectEntity prj = new ProjectEntity();
		prj.setId(form.getProjectId());

		ProjectClientEntity rel = new ProjectClientEntity(
				null, prj, saved, form.getRepName(), form.getNote());

		return rel;
	}

	public void deleteByIdIfExists(int id) {
		if (clientRepo.existsById(id)) {
			clientRepo.deleteById(id);
		}
	}

}
