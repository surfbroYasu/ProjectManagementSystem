package com.example.projectmanagement.modules.databases.services.application.context;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.projectmanagement.modules.databases.services.application.context.column.DbColumnContextService;
import com.example.projectmanagement.modules.databases.services.application.context.database.DbInfoContextService;
import com.example.projectmanagement.modules.databases.services.application.context.multi.ColumnMultiContextService;
import com.example.projectmanagement.modules.databases.services.application.context.multi.TableMultiContextService;
import com.example.projectmanagement.modules.databases.services.application.context.table.DbTableContextService;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;

public abstract class AbstractPageContextService extends ProjectViewContextService {
	@Autowired
	protected DbInfoContextService dbContext;
	@Autowired
	protected DbTableContextService tableContext;
	@Autowired
	protected DbColumnContextService columnContext;
	@Autowired
	protected ColumnMultiContextService columnMultiContext;
	@Autowired
	protected TableMultiContextService tableMultiContext;
	@Autowired
	protected ColumnMultiContextService colMultiContext;
	
}
