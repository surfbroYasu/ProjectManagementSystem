package com.example.projectmanagement.modules.coding.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{project}/model")
public class ModelGenerateController {

	private static final String TEMPLATE_ROOT = "contents/coding/model";

	@GetMapping("/database-entity/{lang}")
	public String renderModelGeneratorSetupForm(@PathVariable Integer projectId,
	        @PathVariable String lang) {
		/*
		 * TODO: What data is required
		 * 1. programing lang to convert to 
		 * 2. database table and of which columns
		 */
		return TEMPLATE_ROOT;
	}
	
	@PostMapping("/database-entity")
	public String something(){
		return null;
	}

}
