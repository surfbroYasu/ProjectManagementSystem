package com.example.projectmanagement.modules.databases.services.application.context.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;
import com.example.projectmanagement.modules.databases.services.application.context.ModelAttributes;
import com.example.projectmanagement.modules.databases.services.application.context.table.TablePageContextService;

@Service
public class ColumnPageContextService {

	@Autowired
	private TablePageContextService tablePageContext;
	
	public void setupColumnFormErrorPage(Model model,
			Integer projectId,
			Integer databaseId,
			TableColumnRegisterForm form,
			String title) {

		model.addAttribute(ModelAttributes.ERROR_COLUMN_ID, form.getId());
		tablePageContext.setupTableDetailPage(model, projectId, databaseId, form.getTableInfoId(), title);
	}
	
}
