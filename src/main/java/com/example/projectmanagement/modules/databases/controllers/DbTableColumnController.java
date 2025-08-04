package com.example.projectmanagement.modules.databases.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.context.column.ColumnPageContextService;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnValidationService;
import com.example.projectmanagement.modules.databases.services.repository.DbColumnRepositoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DbTableColumnController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	@Qualifier("column")
	private ColumnPageContextService contextService;

	@Autowired
	private DbColumnRepositoryService repoService;

	@Autowired
	private ColumnValidationService validationService;


//	@Autowired
//	private EntityFieldService entityService;
//
//	@Autowired
//	private ClassDefRepositoryService classDefService;

	@PostMapping("/{databaseId}/table/{tableId}/column")
	public String handleColumnAction(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			@RequestParam("action") String action,
			@Validated @ModelAttribute("tableColumnRegisterForm") TableColumnRegisterForm form,
			BindingResult bindingResult,
			Model model,
			Locale locale) {

		String redirectUrl = "redirect:" + request.getHeader("Referer");

		if ("delete".equals(action)) {
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}

		validationService.setFalseToNull(form);
		validationService.validateForm(bindingResult, databaseId, form);

		if (bindingResult.hasErrors()) {
			String title = "title.db.tables";
			contextService.setupColumnFormErrorPage(model, projectId, databaseId, form, title);

			return TEMPLATE_ROOT + "detailTable";
		}

		repoService.saveByAction(action, databaseId, form);

		return "redirect:/project/" + projectId + "/database/" + databaseId + "/table/" + tableId;
	}
}
