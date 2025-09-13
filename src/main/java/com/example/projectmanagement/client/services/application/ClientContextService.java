package com.example.projectmanagement.client.services.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.client.datastructures.dtos.ClientDtoRecord;
import com.example.projectmanagement.client.datastructures.entities.ClientBasicInfoEntity;
import com.example.projectmanagement.client.datastructures.entities.ProjectClientEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientForm;
import com.example.projectmanagement.client.repository.ClientInfoJpaRepository;
import com.example.projectmanagement.client.repository.ProjectClientJpaRepository;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;

@Service("client")
public class ClientContextService extends ProjectViewContextService {
	@Autowired
	private ClientInfoJpaRepository clientRepo;

	@Autowired
	private ProjectClientJpaRepository projectClientJpa;

	public void setDetail(Model model, Integer projectId, String titleProp) {

		setPageTitle(model, titleProp);
		setProjectToModel(model, projectId);

		ProjectClientEntity client = projectClientJpa.findByProjectId(projectId)
				.orElseThrow(() -> new IllegalArgumentException("project client not found"));

		Optional.ofNullable(client)
				.map(this::convert)
				.ifPresentOrElse(
						record -> model.addAttribute("client", record),
						() -> model.addAttribute("clientNotFound", true));

	}
	

	public void setClientOptions(List<ClientBasicInfoEntity> options, Integer projectId, String title, Model model) {
		
		setPageTitle(model, title);
		setProjectToModel(model, projectId);
		model.addAttribute("clientOptions", options);
	}
	
	public void setPhoneInputPage( int projectId, String title, Model model) {
		setPageTitle(model, title);
		setProjectToModel(model, projectId);
		model.addAttribute("formType", "phone");
	}
	

	private ClientDtoRecord convert(ProjectClientEntity entity) {
		return new ClientDtoRecord(
				entity.getId(),
				entity.getClient().getName(),
				entity.getRepName(),
				entity.getClient().getPhone(),
				entity.getNote());
	}


	public List<ClientBasicInfoEntity> searchFetchClientByPhone(String phone) {
		return clientRepo.findAllByPhone(phone);
	}

	public void fillUpFormWithDefault(ClientForm form, Integer clientId, Integer projectId, String phone) {
		form.setPhone(phone);
		form.setProjectId(projectId);

		ClientBasicInfoEntity client = clientRepo.findById(clientId).orElse(null);
		if (client != null) {
			form.setName(client.getName());
			form.setOrganizationId(client.getOrganization().getId());
		}

		ProjectClientEntity projectClient = projectClientJpa.findByProjectId(projectId).orElse(null);
		if (projectClient != null) {
			form.setRepName(projectClient.getRepName());
			form.setNote(projectClient.getNote());
			form.setId(projectClient.getId());
		}
	}

}
