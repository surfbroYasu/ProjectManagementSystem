package com.example.projectmanagement.client.controllers;

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

import com.example.projectmanagement.client.datastructures.entities.ClientEntity;
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
	
	
	private static final String TEMPLATE_ROOT = "contents/client/";

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

	@GetMapping("/register")
	public String renderClientForm(Model model) {
		
		return TEMPLATE_ROOT + "register";
	}

	@PostMapping("/{action}")
	public String editClient(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable String action,
			@Validated @ModelAttribute("clientForm") ClientForm form, BindingResult bindingResult
			) {
		String referer = request.getHeader("Referer");
		String redirectUrl = "redirect:" + referer;
		
	    if ("delete".equals(action)) {
	        repoService.deleteByIdIfExists(form.getId());
	        return redirectUrl;
	    }

		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		ClientEntity clientEntity = new ClientEntity();
		BeanUtils.copyProperties(form, clientEntity);

	    repoService.saveByAction(action, form);
	    
		return redirectUrl;
	}
	
	
}
