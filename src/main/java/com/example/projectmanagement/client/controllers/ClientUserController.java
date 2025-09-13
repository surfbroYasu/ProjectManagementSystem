package com.example.projectmanagement.client.controllers;

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

import com.example.projectmanagement.client.datastructures.forms.ClientUserInfoForm;
import com.example.projectmanagement.client.services.application.ClientUserContextService;
import com.example.projectmanagement.client.services.repository.ClientUserRepositoryService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * このコントローラークラスはクライアント担当者のCRUDページのエンドポイントを制御するクラスです
 * 
 * [[ GET ]]
 * ・/project/{projectId}/client/{clientId}/personnel  : クライアント担当者一覧ページURL
 * ・/project/{projectId}/client/{clientId}/personnel/{clientUserId}  : クライアント担当者詳細ページURL
 * 
 * [[ POST ]]
 * ・/project/{projectId}/client/{clientId}/personnel/{action} : クライアント担当者登録エンドポイントURL
 * 
 * 
 * @author yasufumimisono
 *
 */
@Controller
@RequestMapping("/project/{projectId}/client/{clientId}")
public class ClientUserController {
	
	@Autowired
	private ClientUserRepositoryService repoService;

	@Autowired
	@Qualifier("clientUser")
	private ClientUserContextService contextService;

	@ModelAttribute("clientUserInfoForm")
	private ClientUserInfoForm setClientUserForm() {
		return new ClientUserInfoForm();
	}

	private static final String TEMPLATE_ROOT = "contents/client/personnel/";

	/**
	 * クライアント担当者情報一覧
	 * @param model
	 * @param projectId
	 * @param clientId
	 * @return
	 */
	@GetMapping("/personnel")
	public String renderClientUserList(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer clientId) {

		String title = "title.personnel.list";
		contextService.setClientUserListContext(model, projectId, clientId, title);

		return TEMPLATE_ROOT + "list";
	}

	/**
	 * クライアント担当者情報詳細
	 * @param model
	 * @param projectId
	 * @param clientId
	 * @param personnelId
	 * @return
	 */
	@GetMapping("/personnel/{personnelId}")
	public String renderClientUserDetail(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer clientId,
			@PathVariable Integer personnelId) {

		String title = "title.personnel.detail";
		contextService.setClientUserDetail(model, projectId, personnelId, title);

		return TEMPLATE_ROOT + "list";
	}


	/**
	 * List of actions:: add, edit, delete
	 * 
	 * @param request
	 * @param projectId
	 * @param action
	 * @param form
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/{action}")
	public String editClientUser(HttpServletRequest request,
	        @PathVariable Integer projectId,
	        @PathVariable Integer clientId,
	        @PathVariable String action,
	        @Validated @ModelAttribute("clientUserInfoForm") ClientUserInfoForm form,
	        BindingResult bindingResult) {

	    String referer = request.getHeader("Referer");
	    String redirectUrl = "redirect:" + referer;

	    if ("delete".equals(action)) {
	        repoService.deleteByIdIfExists(form.getId());
	        return redirectUrl;
	    }

	    if (bindingResult.hasErrors()) {
	        return redirectUrl;
	    }

	    repoService.saveByAction(action, form);
	    return redirectUrl;
	}


}
