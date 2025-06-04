package com.example.projectmanagement.modules.coding.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.modules.coding.services.application.ClassDefContextService;

@Controller
@RequestMapping("/project/{projectId}/classdef")
public class ClassDefController {

	@Autowired
	private ClassDefContextService contextService;

	private static final String TEMPLATE_ROOT = "contents/coding/classdef";

	@GetMapping("/database-entity/{tableId}")
	public String renderModelGeneratorSetupForm(@PathVariable Integer projectId,
			@PathVariable("tableId") Integer tableId,
			@RequestParam("lang") String lang, Model model) {

		model.addAttribute("title", "title.class_def");
		contextService.setEntityView(model, lang, tableId, "entity");
		
		return TEMPLATE_ROOT + "/codeBlock";
	}

}
