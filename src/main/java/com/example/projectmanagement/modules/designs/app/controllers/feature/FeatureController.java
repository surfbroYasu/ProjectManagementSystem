package com.example.projectmanagement.modules.designs.app.controllers.feature;

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

import com.example.projectmanagement.modules.designs.app.datastructures.entity.ApplicationFeatureEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.entity.ModuleFeatureRelationEntity;
import com.example.projectmanagement.modules.designs.app.datastructures.form.ApplicationFeatureForm;
import com.example.projectmanagement.modules.designs.app.service.application.FeaturePageContext;
import com.example.projectmanagement.modules.designs.app.service.repository.FeatureRepositoryService;

@Controller
@RequestMapping("/project/{projectId}/feature")
public class FeatureController {

	@Autowired
	private FeaturePageContext contextService;
//	private FeatureContextService contextService;

	@Autowired
	private FeatureRepositoryService domainService;

	@ModelAttribute("featureForm")
	private ApplicationFeatureForm setFeatureDefForm() {
		return new ApplicationFeatureForm();
	};

	private static final String TEMPLATE_ROOT = "contents/design/app/feature";

	@GetMapping("s/all")
	public String renderModuleFeaturesList(@PathVariable Integer projectId, Model model) {
		String pageTitleMK = "title.module_def.list";

		contextService.setupFeatureListPage(model, projectId, pageTitleMK);
		return TEMPLATE_ROOT + "/module_list";
	}

	@PostMapping("/{action}")
	public String moduleCUD(@PathVariable Integer projectId, @PathVariable String action,
			Model model,
			@Validated @ModelAttribute("featureForm") ApplicationFeatureForm form, BindingResult result) {

		String onSuccessUrl = "redirect:/project/" + projectId + "/module/list";

		/*
		 * DELETE method MUST be delt first thing!!!
		 *  does not required validation and other logic
		 */
		if ("delete".equals(action) && form.getId() != null) {
			domainService.deleteFeature(form.getId());
			return onSuccessUrl;
		}

		if (result.hasErrors()) {
			String pageTitleMK = "title.module_def.list";
			contextService.setupFeatureListPage(model, projectId, pageTitleMK);
			return TEMPLATE_ROOT + "/module_list";
		}

		ApplicationFeatureEntity entity = new ApplicationFeatureEntity();
		BeanUtils.copyProperties(form, entity);
		
		ModuleFeatureRelationEntity relations = new ModuleFeatureRelationEntity();
		BeanUtils.copyProperties(form, relations);

		switch (action) {
		case "add" -> domainService.createFeatureNModuleRelation(entity, relations);
		case "update" -> domainService.updateFeature(entity);

		default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
		return onSuccessUrl;
	}

	@GetMapping("/list/module/{moduleId}")
	public String renderFeaturesOfAModule(@PathVariable Integer projectId,
			@PathVariable Integer moduleId,
			Model model) {

		String pageTitleMK = "title.module_feature.list";
		
		contextService.setupRelatedFeaturesOfModulePage(model, projectId, moduleId, pageTitleMK);
		
		return TEMPLATE_ROOT + "/feature_list";
	}
	
	
	
	@GetMapping("/{featureId}/detail")
	public String renderFeatureDetail(@PathVariable Integer projectId, @PathVariable Integer featureId, Model model) {
		model.addAttribute("title", "title.feature.detail");
		contextService.setProjectToModel(model, projectId);
		return TEMPLATE_ROOT + "/feature_detail";
	}
}
