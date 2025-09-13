package com.example.projectmanagement.client.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.client.datastructures.entities.ClientBasicInfoEntity;
import com.example.projectmanagement.client.datastructures.forms.ClientForm;
import com.example.projectmanagement.client.services.application.ClientContextService;
import com.example.projectmanagement.client.services.repository.ClientRepositoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/client")
public class ClientController {

	@Autowired
	private ClientRepositoryService repoService;

	@Autowired
	@Qualifier("client")
	private ClientContextService contextService;

	private static final String TEMPLATE_ROOT = "contents/client/client/";

	@ModelAttribute("clientForm")
	private ClientForm setClientForm() {
		return new ClientForm();
	}

	@GetMapping("")
	public String renderClientDetail(Model model,
			@PathVariable Integer projectId) {
		String title = "title.client.detail";
		contextService.setDetail(model, projectId, title);
		return TEMPLATE_ROOT + "detail";
	}
	
	@GetMapping("register/new")
	public String renderPhoneInputPage(Model model, @PathVariable Integer projectId) {
		
		String title = "titlle.client.phone";
		contextService.setPhoneInputPage(projectId, title, model);
		
		return TEMPLATE_ROOT + "register";
	}

	//	TODO 電話番号で照合できるようにする
	@PostMapping("/search")
	public String searchExistingCompanyByPhone(@PathVariable Integer projectId, @RequestParam String phone, Model  model) {
		List<ClientBasicInfoEntity> options = contextService.searchFetchClientByPhone(phone);
		if (options.size() > 0) {
			return "redirect:/project/" + projectId + "/client/submit?phone=" + phone + "&clientId=null";
		}
		
		String title = "title.client.todo";
		contextService.setClientOptions(options, projectId, title, model);
		return TEMPLATE_ROOT + "option";

	}
	
	
	//照合がヒットしなかった場合は"/project/" + projectId + "/client/register?phone=" + phone + "&clientId=null";
	@GetMapping("/submit")
	public String renderClientForm(Model model,
			@PathVariable Integer projectId,
			@RequestParam String phone,
			@RequestParam String clientId,
			@ModelAttribute("clientForm") ClientForm form) {
		String title = "title.client.detail";
		contextService.setDetail(model, projectId, title);
		
		contextService.fillUpFormWithDefault(form, projectId, projectId, phone);
		
		return TEMPLATE_ROOT + "detail";
	}

	@PostMapping("/{action}")
	public String editClient(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable String action,
			@Validated @ModelAttribute("clientForm") ClientForm form, BindingResult bindingResult) {
		String referer = request.getHeader("Referer");
		String redirectUrl = "redirect:" + referer;

		if ("delete".equals(action)) {
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}

		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		ClientBasicInfoEntity clientEntity = new ClientBasicInfoEntity();
		BeanUtils.copyProperties(form, clientEntity);

		repoService.saveByAction(action, form);

		return redirectUrl;
	}

}
