package com.example.projectmanagement.modules.designs.app.features.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{project}/module")
public class ModuleController {

	private static final String TEMPLATE_ROOT = "contents/design/app/module";

	@GetMapping("/list")
	public String renderModuleList(@PathVariable Integer projectId) {
		/*
		 * TODO: What data is required
		 */
		return TEMPLATE_ROOT;
	}

	@PostMapping("/{action}")
	public String moduleCUD(@PathVariable Integer projectId, @PathVariable String action) {
		switch (action) {
		case "add" -> System.out.println(action + " Module");
		case "update" -> System.out.println(action + " Module");
		case "delete" -> System.out.println(action + " Module");

		default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
		return null;
	}

}
