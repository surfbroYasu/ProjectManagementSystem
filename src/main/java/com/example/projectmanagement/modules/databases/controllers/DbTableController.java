package com.example.projectmanagement.modules.databases.controllers;

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
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableService;
import com.example.projectmanagement.modules.projects.services.domain.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DbTableController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	private DBViewContextService contextService;

	@Autowired
	private DbTableService domainService;

	@Autowired
	private ProjectService projctService;

	@Autowired
	private EntityFieldService entityService;

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
			domainService.deleteTable(form.getId());
			return redirectUrl;
		}

		if (bindingResult.hasErrors()) {
			return redirectUrl;
		}

		TableInfo domain = new TableInfo();
		BeanUtils.copyProperties(form, domain);

		String serverSideLang = projctService.getProjectById(projectId).getServerSideLang();

		switch (action) {
		case "add" -> {
			domainService.insertTable(domain);
			entityService.createClassDefFromTableId(serverSideLang, projectId, domain.getId(), "entity");
		}
		case "edit" -> {
			domainService.updateTable(domain);
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return redirectUrl;

	}

	@GetMapping("/{databaseId}/table/{tableId}")
	public String renderTableDetail(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId) {
		model.addAttribute("title", "title.db.tables");


		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());
		
		contextService.setSQLtoModel(model, db.getDbms(), targetTable);

		return TEMPLATE_ROOT + "detailTable";
	}

	@GetMapping("/{databaseId}/table/{tableId}/print")
	public String printTable(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			Model model) {

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());
		model.addAttribute("print", "print");

		return TEMPLATE_ROOT + "printTable";
	}
}
