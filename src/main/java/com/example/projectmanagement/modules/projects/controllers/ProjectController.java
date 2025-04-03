package com.example.projectmanagement.modules.projects.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private static final String TEMPLATE_ROOT = "contents/projects/";
	
	

	@GetMapping("")
	public String renderProjectIndex(Model model) {
		model.addAttribute("title", "title.project.top");
		return TEMPLATE_ROOT + "list";
	}
}