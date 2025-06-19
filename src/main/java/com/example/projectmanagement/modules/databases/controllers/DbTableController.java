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

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlSyntaxGenerator;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableService;

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
	private DbTableColumnService columnService;

	@Autowired
	private SqlGeneratorFactory sqlFactory;
	
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

		List<TableColumn> columnList = columnService.getTableColumns(List.of(tableId));

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId, columnList);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());

		//	 SQL generator
		SqlSyntaxGenerator sqlGen = sqlFactory.getGenerator(db.getDbms());
		model.addAttribute("createTableSQL", sqlGen.createTable(targetTable, columnList));
		model.addAttribute("dropTableSQL", sqlGen.dropTable(targetTable.getTableName()));

		return TEMPLATE_ROOT + "detailTable";
	}
	
	@GetMapping("/{databaseId}/table/{tableId}/print")
	public String printTable(HttpServletRequest request,
			@PathVariable Integer projectId,
			@PathVariable Integer databaseId,
			@PathVariable Integer tableId,
			Model model) {

		List<TableColumn> columnList = columnService.getTableColumns(List.of(tableId));

		DBInfo db = contextService.setDatabaseContext(model, databaseId);
		TableInfo targetTable = contextService.setSingleTableContext(model, tableId, columnList);
		contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());
		model.addAttribute("print", "print");

		return TEMPLATE_ROOT + "printTable";
	}
}
