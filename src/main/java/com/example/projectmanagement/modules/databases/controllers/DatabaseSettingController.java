package com.example.projectmanagement.modules.databases.controllers;

import java.util.Objects;

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

import com.example.projectmanagement.modules.databases.datastructure.form.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.context.database.DatabasePageContextService;
import com.example.projectmanagement.modules.databases.services.repository.DatabaseRepositoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DatabaseSettingController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	private DatabasePageContextService contextService;

	@Autowired
	private DatabaseRepositoryService repoService;



	@ModelAttribute("dbInfoRegisterForm")
	public DBInfoRegisterForm setDBRegistForm() {
		return new DBInfoRegisterForm();
	}

	@ModelAttribute("tableInfoRegisterForm")
	public TableInfoRegisterForm setTableRegistForm() {
		return new TableInfoRegisterForm();
	}

	@GetMapping("")
	public String showDbInfo(@PathVariable Integer projectId, Model model) {

		String title = "title.db.top";
		contextService.setupDbListPage(model, projectId, title);

		return TEMPLATE_ROOT + "list";
	}

	@PostMapping("/{action}")
	public String handleDatabaseOperation(HttpServletRequest request,
			@PathVariable String action,
			@PathVariable Integer projectId,
			@Validated @ModelAttribute("dbInfoRegisterForm") DBInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {
		
		String redirectUrl =  "redirect:" + request.getHeader("Referer");
		
		if ("delete".equals(action)) {
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}


		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		if (!Objects.equals(form.getProjectId(), projectId)) {
		    form.setProjectId(projectId);
		}
		repoService.saveByAction(action, form);

		return redirectUrl;
	}

	@GetMapping("/{databaseId}/detail")
	public String renderDBDetail(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		String title = "title.db.details";
		contextService.setupDbInfoDetailPage(model, databaseId, projectId, title);

		return TEMPLATE_ROOT + "detailDB";
	}
	
	@GetMapping("/{databaseId}/print")
	public String printDBTables(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		contextService.setupTableDefPrintablePage(model, databaseId);

		return TEMPLATE_ROOT + "printTable";
	}

}
