package com.example.projectmanagement.modules.projects.services.application.context;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.application.constance.enums.SupportedProgramingLangage;
import com.example.projectmanagement.application.context.ApplicationContextService;
import com.example.projectmanagement.modules.projects.constance.ModelAttributes;
import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;

@Service("preProject")
public class PreProjectContextService extends ApplicationContextService {

	public void setProjectList(Model model, List<ProjectDtoRecord> projects) {
		model.addAttribute(ModelAttributes.PROJECT_LIST, projects);
	}

	public void setupServerSideLangOpts(Model model) {
		model.addAttribute(ModelAttributes.SERVERSIDE_LANG_OPT, SupportedProgramingLangage.getStringLanguageCodes());
	}

}
