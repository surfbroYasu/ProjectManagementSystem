package com.example.projectmanagement.client.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.client.datastructures.dtos.ClientDtoRecord;
import com.example.projectmanagement.client.datastructures.entities.ClientEntity;
import com.example.projectmanagement.client.repository.ClientJpaRepository;
import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.services.application.ProjectViewContextService;

@Service
public class ClientContextService extends ProjectViewContextService {

	@Autowired
	private ClientJpaRepository jpaRepo;

	public void setDetail(Model model, int projectId, String titleProp) {

		setPageTitle(model, titleProp);
		Project project = setProjectToModel(model, projectId);

		jpaRepo.findById(project.getClientId())
				.map(this::convert)
				.ifPresentOrElse(
						record -> model.addAttribute("client", record),
						() -> {
							model.addAttribute("clientNotFound", true);
						});
	}

	private ClientDtoRecord convert(ClientEntity entity) {
		return new ClientDtoRecord(
				entity.getId(),
				entity.getName(),
				entity.getNote(),
				entity.getPhone(),
				entity.getRepName());
	}

}
