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
import com.example.projectmanagement.modules.databases.domain.TableInfo;
import com.example.projectmanagement.modules.databases.forms.DBInfoRegisterForm;
import com.example.projectmanagement.modules.databases.forms.TableInfoRegisterForm;
import com.example.projectmanagement.modules.databases.services.DatabaseService;
import com.example.projectmanagement.modules.projects.services.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/database")
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
	
	
	@GetMapping("/{projectId}")
	public String showDbInfo(@PathVariable Integer projectId, Model model) {
	    model.addAttribute("title", "title.db.top");
	    model.addAttribute("project", projectService.getProjectById(projectId));
	    
	    List<DBInfo> dbInfo = service.getAll(projectId);
	    model.addAttribute("dbInfo", dbInfo);

	    Map<Integer, List<TableInfo>> tableInfoMap = service.getRelatedTableInfo(dbInfo);
	    model.addAttribute("tableInfoMap", tableInfoMap);

	    return TEMPLATE_ROOT + "list";
	}

	
	@PostMapping("/add")
	public String addDatabase(HttpServletRequest request,
			@Validated @ModelAttribute("dbInfoRegisterForm") DBInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {

	    String referer = request.getHeader("Referer");

		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}

		DBInfo domain = new DBInfo();
		BeanUtils.copyProperties(form, domain);

		service.insertDatabase(domain);

		return "redirect:" + referer;
	}
	
	@GetMapping("/detail/{databaseId}")
	public String renderDBDetail(@PathVariable Integer databaseId, Model model) {
		
		DBInfo targetDB = service.getDBInfoByDBId(databaseId);
		model.addAttribute("title", "title.db.details");

	    model.addAttribute("project", projectService.getProjectById(targetDB.getProjectId()));
		model.addAttribute("db", targetDB);
		return TEMPLATE_ROOT + "detailDB";
	}
	
	
	
	@PostMapping("/table/add")
	public String addTable(HttpServletRequest request,
			@Validated @ModelAttribute("tableInfoRegisterForm") TableInfoRegisterForm form,
			BindingResult bindingResult,
			Model model) {
		
		String referer = request.getHeader("Referer");
		
		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}
		
		TableInfo domain = new TableInfo();
		BeanUtils.copyProperties(form, domain);
		
		service.insertTable(domain);
		
		return "redirect:" + referer;
	}

	
	@GetMapping("/table/{tableId}")
	public String renderTableDetail(@PathVariable Integer tableId, Model model) {
		model.addAttribute("title", "title.db.tables");
		
		TableInfo targetTable = service.getTableByTableId(tableId);
		DBInfo targetDB = service.getDBInfoByDBId(targetTable.getDbInfoId());

	    model.addAttribute("project", projectService.getProjectById(targetDB.getProjectId()));
		model.addAttribute("db", targetDB);
		model.addAttribute("table", targetTable);

		return TEMPLATE_ROOT + "detailTable";
	}
	

}
