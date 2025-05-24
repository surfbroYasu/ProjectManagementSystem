package com.example.projectmanagement.modules.databases.controllers;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlSyntaxGenerator;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnValidationService;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;

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
	private DataTypeResolverFactory resolverFactory;

	@Autowired
	private ColumnValidationService validationService;

	@Autowired
	private SqlGeneratorFactory sqlFactory;

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

		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}

		DBInfo domain = new DBInfo();
		BeanUtils.copyProperties(form, domain);
		domain.setProjectId(projectId);

		switch (action) {
		case "add" -> domainService.insertDatabase(domain);
		case "edit" -> domainService.updateDatabase(domain);
		case "delete" -> domainService.deleteDatabase(domain.getId());
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:" + referer;
	}

	@GetMapping("/{databaseId}/detail")
	public String renderDBDetail(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		model.addAttribute("title", "title.db.details");

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		contextService.setDatabaseTablesContext(model, databaseId);
		contextService.prepareColumnFormForModel(model, databaseId, db.getDbms());

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
		case "add" -> domainService.insertTable(domain);
		case "edit" -> domainService.updateTable(domain);
		case "delete" -> domainService.deleteTable(domain.getId());
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:" + referer;
	}

	@GetMapping("/{databaseId}/table/{tableId}")
	public String renderTableDetail(Model model,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId) {
		model.addAttribute("title", "title.db.tables");

		List<TableColumn> columnList = domainService.getTableColumns(List.of(tableId));

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId, columnList);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());

		//	 SQL generator
		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(db.getDbms());
		model.addAttribute("createTableSQL", sqlGen.createTable(targetTable, columnList));
		model.addAttribute("dropTableSQL", sqlGen.dropTable(targetTable.getTableName()));

		return TEMPLATE_ROOT + "detailTable";
	}

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
			domainService.deleteColumn(form.getId());
			return redirectUrl;
		}

		validationService.setFalseToNull(form);
		String dbms = domainService.getDBInfoByDBId(databaseId).getDbms();
		validationService.validateForm(bindingResult, dbms, form);

		if (bindingResult.hasErrors()) {

			model.addAttribute("errorColumnId", form.getId());

			model.addAttribute("title", "title.db.tables");

			List<TableColumn> columnList = domainService.getTableColumns(List.of(tableId));

			DBInfo db = contextService.setDatabaseContext(model, databaseId);
			TableInfo targetTable = contextService.setSingleTableContext(model, tableId, columnList);
			contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());

			DataTypeResolver dataTypeResolver = resolverFactory.getResolver(dbms);
			model.addAttribute("dataTypeResolver", dataTypeResolver);

			SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(db.getDbms());
			model.addAttribute("createTableSQL", sqlGen.createTable(targetTable, columnList));
			model.addAttribute("dropTableSQL", sqlGen.dropTable(targetTable.getTableName()));

			return TEMPLATE_ROOT + "detailTable";
		}

		TableColumn domain = new TableColumn();
		BeanUtils.copyProperties(form, domain);

		switch (action) {
		case "add" -> domainService.insertColumn(domain);
		case "edit" -> domainService.updateColumn(domain);
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:/project/" + projectId + "/database/" + databaseId + "/table/" + tableId;
	}

	@GetMapping("/{databaseId}/print")
	public String printDBTables(@PathVariable Integer projectId, @PathVariable Integer databaseId, Model model) {

		contextService.setDatabaseContext(model, databaseId);
		contextService.setDatabaseTablesContext(model, databaseId);

		return TEMPLATE_ROOT + "printTable";
	}

	@GetMapping("/{databaseId}/table/{tableId}/print")
	public String printTable(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			Model model) {

		List<TableColumn> columnList = domainService.getTableColumns(List.of(tableId));

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId, columnList);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());
		model.addAttribute("print", "print");

		return TEMPLATE_ROOT + "printTable";
	}

}
