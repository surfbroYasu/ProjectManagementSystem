package com.example.projectmanagement.modules.databases.controllers;

import java.util.List;
import java.util.Map;

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

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableColumn;
import com.example.projectmanagement.modules.databases.domain.TableInfo;
import com.example.projectmanagement.modules.databases.forms.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.forms.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.forms.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.DatabaseService;
import com.example.projectmanagement.modules.projects.services.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DatabaseSettingController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DatabaseService service;

	@ModelAttribute("dbInfoRegisterForm")
	public DBInfoRegisterForm setDBRegistForm() {
		return new DBInfoRegisterForm();
	}

	@ModelAttribute("tableInfoRegisterForm")
	public TableInfoRegisterForm setTableRegistForm() {
		return new TableInfoRegisterForm();
	}

	private void setProjectToModel(Model model, Integer projectId) {
		model.addAttribute("project", projectService.getProjectById(projectId));
	}

	private void setProjectFromDatabase(Model model, Integer databaseId) {
		DBInfo db = service.getDBInfoByDBId(databaseId);
		model.addAttribute("project", projectService.getProjectById(db.getProjectId()));
		model.addAttribute("db", db);
	}

	private void setProjectFromTable(Model model, Integer tableId) {
		TableInfo table = service.getTableByTableId(tableId);
		DBInfo db = service.getDBInfoByDBId(table.getDbInfoId());
		model.addAttribute("project", projectService.getProjectById(db.getProjectId()));
		model.addAttribute("db", db);
		model.addAttribute("table", table);
	}

	/**
	 * prep Foreign Key select options 
	 * @param model
	 * @param databaseId
	 */
	private void setColumnFormToModel(Model model, Integer databaseId) {
		TableColumnRegisterForm form = new TableColumnRegisterForm();
		form.setFkOptions(service.getFKList(databaseId));
		model.addAttribute("tableColumnRegisterForm", form);
	}

	@GetMapping("")
	public String showDbInfo(@PathVariable Integer projectId, Model model) {
		model.addAttribute("title", "title.db.top");
		setProjectToModel(model, projectId);

		List<DBInfo> dbInfo = service.getAll(projectId);
		model.addAttribute("dbList", dbInfo);

		Map<Integer, List<TableInfo>> tableInfoMap = service.getRelatedTableInfo(dbInfo);
		model.addAttribute("tableInfoMap", tableInfoMap);

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

		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}

		DBInfo domain = new DBInfo();
		BeanUtils.copyProperties(form, domain);
		domain.setProjectId(projectId); // projectIdがDBInfoに必要な場合

		switch (action) {
		case "add":
			service.insertDatabase(domain);
			break;
		case "edit":
			service.updateDatabase(domain);
			break;
		case "delete":
			service.deleteDatabase(domain.getId());
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:" + referer;
	}

	@GetMapping("/{databaseId}/detail")
	public String renderDBDetail(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		List<TableInfo> relatedTables = service.getTableInfoByDbIds(List.of(databaseId));

		model.addAttribute("title", "title.db.details");

		setProjectFromDatabase(model, databaseId);

		model.addAttribute("tableList", relatedTables);
		model.addAttribute("columnMap", service.getRelatedColumns(relatedTables));

		setColumnFormToModel(model, databaseId);

		return TEMPLATE_ROOT + "detailDB";
	}

	@PostMapping("/{databaseId}/table/{action}")
	public String addTable(HttpServletRequest request,
			@PathVariable String action,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@Validated @ModelAttribute("tableInfoRegisterForm") TableInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		String referer = request.getHeader("Referer");

		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}

		TableInfo domain = new TableInfo();
		BeanUtils.copyProperties(form, domain);

		switch (action) {
		case "add":
			service.insertTable(domain);
			break;
		case "edit":
			service.updateTable(domain);
			break;
		case "delete":
			service.deleteTable(domain.getId());
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:" + referer;
	}

	@GetMapping("/{databaseId}/table/{tableId}")
	public String renderTableDetail(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId) {
		model.addAttribute("title", "title.db.tables");

		TableInfo targetTable = service.getTableByTableId(tableId);
		List<TableColumn> columnList = service.getTableColumns(List.of(targetTable.getId()));

		setColumnFormToModel(model, targetTable.getDbInfoId());
		setProjectFromTable(model, tableId);
		model.addAttribute("columnList", columnList);

		return TEMPLATE_ROOT + "detailTable";
	}

	@PostMapping("/{databaseId}/table/{tableId}/column/{action}")
	public String addColumn(HttpServletRequest request,
			@PathVariable String action,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			@Validated @ModelAttribute("tableColumnRegisterForm") TableColumnRegisterForm form, BindingResult result,
			Model model) {
		String referer = request.getHeader("Referer");

		if (result.hasErrors()) {
			return "redirect:" + referer;
		}

		TableColumn domain = new TableColumn();
		BeanUtils.copyProperties(form, domain);

		switch (action) {
		case "add":
			System.out.println(domain);
			service.insertColumn(domain);
			break;
		case "edit":
			service.updateColumn(domain);
			break;
		case "delete":
			service.deleteColumn(domain.getId());
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action);
		}
		return "redirect:" + referer;
	}

	
	@GetMapping("/{databaseId}/print")
	public String printDBTables(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		List<TableInfo> relatedTables = service.getTableInfoByDbIds(List.of(databaseId));

		setProjectFromDatabase(model, databaseId);

		model.addAttribute("tableList", relatedTables);
		model.addAttribute("columnMap", service.getRelatedColumns(relatedTables));


		return TEMPLATE_ROOT + "printTable";
	}
	
	@GetMapping("/{databaseId}/table/{tableId}/print")
	public String printTable(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			Model model) {

		TableInfo targetTable = service.getTableByTableId(tableId);
		List<TableColumn> columnList = service.getTableColumns(List.of(targetTable.getId()));

		setProjectFromTable(model, tableId);
		model.addAttribute("columnList", columnList);
		model.addAttribute("print", "print");
		
		return TEMPLATE_ROOT + "printTable";
	}

}
