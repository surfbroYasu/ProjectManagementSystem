package com.example.projectmanagement.modules.projects.controllers;

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

import com.example.projectmanagement.modules.projects.domain.Project;
import com.example.projectmanagement.modules.projects.forms.ProjectRegisterForm;
import com.example.projectmanagement.modules.projects.services.ProjectService;
import com.example.projectmanagement.users.security.CustomUserDetails;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private static final String TEMPLATE_ROOT = "contents/projects/";

	@Autowired
	private ProjectService service;

	@ModelAttribute("projectRegisterForm")
	public ProjectRegisterForm setRegistForm() {
		return new ProjectRegisterForm();
	}


	@GetMapping("")
	public String renderProjectIndex(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {
		model.addAttribute("title", "title.project.top");
		model.addAttribute("projects", service.getProjects(loginUser.getUserId()));
		return TEMPLATE_ROOT + "list";
	}

	@PostMapping("/add")
	public String addProject(@AuthenticationPrincipal CustomUserDetails loginUser, @Validated @ModelAttribute("projectRegisterForm") ProjectRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("projects", service.getProjects(loginUser.getUserId()));
			model.addAttribute("title", "title.project.top");
			return "contents/projects/list";
		}

		Project domain = new Project();
		BeanUtils.copyProperties(form, domain);

		service.insertProject(domain);
		return "redirect:/projects";
	}

}