package com.example.projectmanagement.modules.databases.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolver;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.DataTypeResolverFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlGeneratorFactory;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.SqlSyntaxGenerator;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnValidationService;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/{projectId}/database")
public class DbTableColumnController {

	private static final String TEMPLATE_ROOT = "contents/databases/";

	@Autowired
	private DBViewContextService contextService;
//
	@Autowired
	private DatabaseService dbService;

	@Autowired
	private DbTableColumnService columnService;

	@Autowired
	private DataTypeResolverFactory resolverFactory;

	@Autowired
	private ColumnValidationService validationService;

	@Autowired
	private SqlGeneratorFactory sqlFactory;
	
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
			columnService.deleteColumn(form.getId());
			return redirectUrl;
		}

		validationService.setFalseToNull(form);
		String dbms = dbService.getDBInfoByDBId(databaseId).getDbms();
		validationService.validateForm(bindingResult, dbms, form);

		if (bindingResult.hasErrors()) {

			model.addAttribute("errorColumnId", form.getId());

			model.addAttribute("title", "title.db.tables");

			List<TableColumn> columnList = columnService.getTableColumns(List.of(tableId));

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
		case "add" -> columnService.insertColumn(domain);
		case "edit" -> columnService.updateColumn(domain);
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:/project/" + projectId + "/database/" + databaseId + "/table/" + tableId;
	}
}
