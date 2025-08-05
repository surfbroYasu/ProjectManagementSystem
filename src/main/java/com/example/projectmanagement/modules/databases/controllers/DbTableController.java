package com.example.projectmanagement.modules.databases.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.context.table.TablePageContextService;
import com.example.projectmanagement.modules.databases.services.repository.DbTableRepositoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DbTableController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	@Qualifier("table")
	private TablePageContextService contextService;

	@Autowired
	private DbTableRepositoryService repoService;

	@PostMapping("/{databaseId}/table/{action}")
	public String addTable(HttpServletRequest request,
			@PathVariable String action,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@Validated @ModelAttribute("tableInfoRegisterForm") TableInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		String referer = request.getHeader("Referer");
		String redirectUrl = "redirect:" + referer;

		if ("delete".equals(action)) {
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}

		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		repoService.saveByAction(action, form);

		return redirectUrl;

	}

	@GetMapping("/{databaseId}/table/{tableId}")
	public String renderTableDetail(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Long tableId) {

		String title = "title.db.tables";

		contextService.setupTableDetailPage(model, projectId, databaseId, tableId, title);

		return TEMPLATE_ROOT + "detailTable";
	}

	@GetMapping("/{databaseId}/table/{tableId}/print")
	public String printTable(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Long tableId,
			Model model) {
		
		contextService.setupTableDefPrintable(model, projectId, databaseId, tableId);

		return TEMPLATE_ROOT + "printTable";
	}
}
