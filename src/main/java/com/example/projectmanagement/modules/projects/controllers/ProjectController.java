package com.example.projectmanagement.modules.projects.controllers;

import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.projects.datastructure.entity.Project;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloper;
import com.example.projectmanagement.modules.projects.datastructure.form.ProjectRegisterForm;
import com.example.projectmanagement.modules.projects.datastructure.model.ProjectDevRoleEnum;
import com.example.projectmanagement.modules.projects.services.application.PreProjectContextService;
import com.example.projectmanagement.modules.projects.services.repository.ProjectService;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private static final String TEMPLATE_ROOT = "contents/projects/";

	@Autowired
	private ProjectService service;
	
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

	@PostMapping("/add")
	public String addProject(Locale locale, @AuthenticationPrincipal CustomUserDetails loginUser,
			@Validated @ModelAttribute("projectRegisterForm") ProjectRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			preProjectContextService.setPerProjectContext(model, loginUser, "title.project.top");
			return "contents/projects/list";
		}

		Project project = new Project();
		BeanUtils.copyProperties(form, project);

		ProjectDeveloper devInfo = new ProjectDeveloper(
				loginUser.getUserId(), //userId
				loginUser.getFormattedFullName(locale), //memberName
				ProjectDevRoleEnum.DEV.name() //devRole
		);

		service.createProjectWithDeveloper(project, devInfo);

		return "redirect:/projects";
	}

	@PostMapping("/edit")
	public String updateProject(@AuthenticationPrincipal CustomUserDetails loginUser,
			@Validated @ModelAttribute("projectRegisterForm") ProjectRegisterForm form,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			preProjectContextService.setPerProjectContext(model, loginUser, "title.project.top");
			return "contents/projects/list";
		}
		Project project = new Project();
		BeanUtils.copyProperties(form, project);
		service.updateProject(project);
		return "redirect:/projects";
	}

	@PostMapping("/delete")
	public String deleteProject(@ModelAttribute("projectRegisterForm") ProjectRegisterForm form) {
		service.deleteProject(form.getId());
		return "redirect:/projects";
	}

}