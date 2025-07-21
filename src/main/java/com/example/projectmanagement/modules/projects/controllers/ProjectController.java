package com.example.projectmanagement.modules.projects.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.projects.datastructure.form.ProjectRegisterForm;
import com.example.projectmanagement.modules.projects.services.application.PreProjectContextService;
import com.example.projectmanagement.modules.projects.services.repository.ProjectRepositoryService;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private static final String TEMPLATE_ROOT = "contents/projects/";

	@Autowired
	private ProjectRepositoryService repoService;

	@Autowired
	private PreProjectContextService preProjectContextService;

	@ModelAttribute("projectRegisterForm")
	public ProjectRegisterForm setRegistForm() {
		return new ProjectRegisterForm();
	}

	@GetMapping("")
	public String renderProjectIndex(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {
		preProjectContextService.setPerProjectContext(model, loginUser, "title.project.top");
		return TEMPLATE_ROOT + "list";
	}

	@PostMapping("/{action}")
	public String addProject(Locale locale, @PathVariable("action") String action,
			@AuthenticationPrincipal CustomUserDetails loginUser,
			@Validated @ModelAttribute("projectRegisterForm") ProjectRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		String redirectUrl = "redirect:/projects";
		
		if ("delete".equals(action)) {
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}

		if (bindingResult.hasErrors()) {
			preProjectContextService.setPerProjectContext(model, loginUser, "title.project.top");
			return "contents/projects/list";
		}

		repoService.saveByAction(action, form, loginUser, locale);

		return redirectUrl;
	}



}