package com.example.projectmanagement.modules.databases.controllers;

import java.util.List;

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

import com.example.projectmanagement.modules.coding.services.application.EntityFieldService;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.repository.DatabaseService;
import com.example.projectmanagement.modules.projects.services.repository.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DatabaseSettingController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	private DBViewContextService contextService;

	@Autowired
	private DatabaseService domainService;
	
	@Autowired
	private ProjectService projctService;
	
	@Autowired
	private EntityFieldService entityService;


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
		model.addAttribute("title", "title.db.top");
		contextService.setProjectToModel(model, projectId);

		List<DBInfo> dbInfo = domainService.getAll(projectId);
		contextService.setAllDatabaseTablesContext(model, dbInfo);

		return TEMPLATE_ROOT + "list";
	}

	@PostMapping("/{action}")
	public String handleDatabaseOperation(HttpServletRequest request,
			@PathVariable String action,
			@PathVariable Integer projectId,
			@Validated @ModelAttribute("dbInfoRegisterForm") DBInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {
		
		String referer = request.getHeader("Referer");
		String redirectUrl =  "redirect:" + referer;
		
		if ("delete".equals(action)) {
			domainService.deleteDatabase(form.getId());
			return redirectUrl;
		}


		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		DBInfo domain = new DBInfo();
		BeanUtils.copyProperties(form, domain);
		domain.setProjectId(projectId);

		switch (action) {
		case "add" -> domainService.insertDatabase(domain);
		case "edit" -> domainService.updateDatabase(domain);
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return redirectUrl;
	}

	@GetMapping("/{databaseId}/detail")
	public String renderDBDetail(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		model.addAttribute("title", "title.db.details");

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		contextService.setDatabaseTablesContext(model, databaseId);
		contextService.prepareColumnFormForModel(model, databaseId, db.getDbms());

		return TEMPLATE_ROOT + "detailDB";
	}
	
	@GetMapping("/{databaseId}/print")
	public String printDBTables(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		contextService.setDatabaseContext(model, databaseId);
		contextService.setDatabaseTablesContext(model, databaseId);

		return TEMPLATE_ROOT + "printTable";
	}

}
