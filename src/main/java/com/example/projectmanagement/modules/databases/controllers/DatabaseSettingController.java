package com.example.projectmanagement.modules.databases.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.example.projectmanagement.modules.databases.domain.DBInfo;
import com.example.projectmanagement.modules.databases.domain.TableColumn;
import com.example.projectmanagement.modules.databases.domain.TableInfo;
import com.example.projectmanagement.modules.databases.forms.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.forms.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.forms.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.ColumnValidationService;
import com.example.projectmanagement.modules.databases.services.DatabaseService;
import com.example.projectmanagement.modules.databases.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.modules.databases.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.sqlgenerator.SqlSyntaxGenerator;
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

	@Autowired
	private DataTypeResolverFactory resolverFactory;

	@Autowired
	private ColumnValidationService validationService;
	
	@Autowired
	private MessageSource messageSource;


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

	private void setProjectToModel(Model model, Integer projectId) {
		model.addAttribute("project", projectService.getProjectById(projectId));
	}

	private DBInfo setProjectFromDatabase(Model model, Integer databaseId) {
		DBInfo db = service.getDBInfoByDBId(databaseId);
		model.addAttribute("project", projectService.getProjectById(db.getProjectId()));
		model.addAttribute("db", db);
		return db;
	}

	private DBInfo setProjectFromTable(Model model, Integer tableId) {
		TableInfo table = service.getTableByTableId(tableId);
		DBInfo db = service.getDBInfoByDBId(table.getDbInfoId());
		model.addAttribute("project", projectService.getProjectById(db.getProjectId()));
		model.addAttribute("db", db);
		model.addAttribute("table", table);
		return db;
	}

	/**
	 * prep Foreign Key select options 
	 * @param model
	 * @param databaseId
	 */
	private void setColumnFormToModel(Model model, Integer databaseId, String dbms) {
		TableColumnRegisterForm form = new TableColumnRegisterForm();
		form.setForignOptions(service.getFKList(databaseId));
		model.addAttribute("tableColumnRegisterForm", form);
		DataTypeResolver dataTypeResolver = resolverFactory.getResolver(dbms);
		model.addAttribute("dataTypeResolver", dataTypeResolver);
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
		domain.setProjectId(projectId);

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

		DBInfo db = setProjectFromDatabase(model, databaseId);

		model.addAttribute("tableList", relatedTables);
		model.addAttribute("columnMap", service.getRelatedColumns(relatedTables));

		setColumnFormToModel(model, databaseId, db.getDbms());

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
		model.addAttribute("columnList", columnList);

		DBInfo db = setProjectFromTable(model, tableId);
		setColumnFormToModel(model, targetTable.getDbInfoId(), db.getDbms());
	
		//		TODO : SQL generator
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
	        service.deleteColumn(form.getId());
	        return redirectUrl;
	    }

	    validationService.setFalseToNull(form);
	    String dbms = service.getDBInfoByDBId(databaseId).getDbms();
	    validationService.validateForm(bindingResult, dbms, form);

	    if (bindingResult.hasErrors()) {
	    	System.out.println(bindingResult);
	    	
	        model.addAttribute("errorColumnId", form.getId());
	        
			model.addAttribute("title", "title.db.tables");

			TableInfo targetTable = service.getTableByTableId(tableId);
			List<TableColumn> columnList = service.getTableColumns(List.of(targetTable.getId()));
			model.addAttribute("columnList", columnList);

			DBInfo db = setProjectFromTable(model, tableId);
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
	        case "add" -> service.insertColumn(domain);
	        case "edit" -> service.updateColumn(domain);
	        default -> throw new IllegalArgumentException("Unsupported action: " + action);
	    }

	    return "redirect:/project/" + projectId + "/database/" + databaseId + "/table/" + tableId;
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
