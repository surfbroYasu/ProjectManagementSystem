package com.example.projectmanagement.modules.databases.controllers;

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

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;
import com.example.projectmanagement.modules.coding.services.application.EntityFieldService;
import com.example.projectmanagement.modules.coding.services.domain.ClassDefDomainService;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.DBViewContextService;
import com.example.projectmanagement.modules.databases.services.application.validation.columnstructure.ColumnValidationService;
import com.example.projectmanagement.modules.databases.services.domain.DatabaseService;
import com.example.projectmanagement.modules.databases.services.domain.DbTableColumnService;
import com.example.projectmanagement.modules.projects.services.domain.ProjectService;

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
	private ColumnValidationService validationService;

	@Autowired
	private ProjectService projctService;

	@Autowired
	private EntityFieldService entityService;

	@Autowired
	private ClassDefDomainService classDefService;

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

			DBInfo db = contextService.setDatabaseContext(model, databaseId);
			TableInfo targetTable = contextService.setSingleTableContext(model, tableId);
			contextService.prepareColumnFormForModel(model, targetTable.getDbInfoId(), db.getDbms());

			contextService.setSQLtoModel(model, dbms, targetTable);

			return TEMPLATE_ROOT + "detailTable";
		}

		TableColumn domain = new TableColumn();
		BeanUtils.copyProperties(form, domain);

		String serverSideLang = projctService.getProjectById(projectId).getServerSideLang();
		ClassDefinition classDef = classDefService.findClassDefinitionByTableId(domain.getTableInfoId());

		switch (action) {
		case "add" -> {
			columnService.insertColumn(domain, dbms);
			entityService.createEntityFieldFromTableCol(serverSideLang, projectId, dbms, classDef.getId(), domain);
		}
		case "edit" -> {
			columnService.updateColumn(domain, dbms);
		}
		default -> throw new IllegalArgumentException("Unsupported action: " + action);
		}

		return "redirect:/project/" + projectId + "/database/" + databaseId + "/table/" + tableId;
	}
}
