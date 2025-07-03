package com.example.projectmanagement.modules.designs.app.modules.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.entity.ModuleDefinitionEntity;
import com.example.projectmanagement.modules.designs.app.modules.datastructures.form.ModuleDefinitionForm;
import com.example.projectmanagement.modules.designs.app.modules.services.application.ModuleContextService;
import com.example.projectmanagement.modules.designs.app.modules.services.repository.ModuleRepostitoryService;

@Controller
@RequestMapping("/project/{projectId}/module")
public class ModuleController {

	@Autowired
	private ModuleContextService contextService;

	@Autowired
	private ModuleRepostitoryService domainService;

	@ModelAttribute("moduleDefForm")
	private ModuleDefinitionForm setModuleDefForm() {
		return new ModuleDefinitionForm();
	};

	private static final String TEMPLATE_ROOT = "contents/design/app/module";

	@GetMapping("/list")
	public String renderModuleList(@PathVariable Integer projectId, Model model) {
		/*
		 * TODO: What data is required
		 */
		String pageTitleMK = "title.module_def.list";
		contextService.setAllModuleEntitiesToModel(model, projectId, pageTitleMK);
		return TEMPLATE_ROOT + "/module_list";
	}

	@PostMapping("/{action}")
	public String moduleCUD(@PathVariable Integer projectId, @PathVariable String action,
			Model model,
			@Validated @ModelAttribute("moduleDefForm") ModuleDefinitionForm form, BindingResult result) {

		String onSuccessUrl = "redirect:/project/" + projectId + "/module/list";

		/*
		 * DELETE method MUST be delt first thing!!!
		 *  does not required validation and other logic
		 */
		if ("delete".equals(action) && form.getId() != null) {
			domainService.deleteModule(form.getId());
			return onSuccessUrl;
		}

		if (result.hasErrors()) {
			contextService.setAllModuleEntitiesToModel(model, projectId, "test");
			return TEMPLATE_ROOT + "/module_list";
		}

		ModuleDefinitionEntity entity = new ModuleDefinitionEntity();
		BeanUtils.copyProperties(form, entity);

		switch (action) {
		case "add" -> domainService.createModule(entity);
		case "update" -> domainService.updateModule(entity);

		default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
		return onSuccessUrl;
	}

}
