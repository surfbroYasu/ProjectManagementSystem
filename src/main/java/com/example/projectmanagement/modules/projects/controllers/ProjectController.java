package com.example.projectmanagement.modules.projects.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.projects.constance.PageTitleEnum;
import com.example.projectmanagement.modules.projects.datastructure.form.ProjectRegisterForm;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectPageContext;
import com.example.projectmanagement.modules.projects.services.repository.ProjectRepositoryService;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private static final String TEMPLATE_ROOT = "contents/projects/";

	@Autowired
	private ProjectRepositoryService repoService;

	@Autowired
	private ProjectPageContext pageContext;

	@ModelAttribute("projectRegisterForm")
	public ProjectRegisterForm setRegistForm() {
		return new ProjectRegisterForm();
	}

	@GetMapping("/list")
	public String renderProjectIndex(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {
		//TODO プロジェクトの削除権限を持った人しか削除できないように、ボタンを表示するか否かのModelAttributeがひつよう
		pageContext.setupProjectListPageByUserId(model, loginUser, PageTitleEnum.PROJECT_TOP.getTitleKey());
		return TEMPLATE_ROOT + "list";
	}

	@PostMapping("/{action}")
	public String addProject(Locale locale, @PathVariable("action") String action,
			@AuthenticationPrincipal CustomUserDetails loginUser,
			@Validated @ModelAttribute("projectRegisterForm") ProjectRegisterForm form,
			BindingResult bindingResult,
			Model model) {

		String redirectUrl = "redirect:/project";

		if ("delete".equals(action)) {
			//TODO 以下サービスに削除権限を持った人からのアクセスか確かめるロジックを追加する
			//-> deleteByIdIfExistsAndAuthorized を作成
			repoService.deleteByIdIfExists(form.getId());
			return redirectUrl;
		}

		if (bindingResult.hasErrors()) {
			pageContext.setupProjectListPageByUserId(model, loginUser, PageTitleEnum.PROJECT_TOP.getTitleKey());
			return "contents/projects/list";
		}
		repoService.saveByAction(action, form, loginUser, locale);
		//TODO 以下サービスに編集権限を持った人からのアクセスか確かめるロジックを追加する
		//-> saveByIdIfExistsAndAuthorized を作成
		//編集と新規作成を分けて実装する方がロジックビルドしやすいかも

		return redirectUrl;
	}

	@GetMapping("/{projectId}/detail")
	public String renderProjectDetail(@PathVariable("projectId") Integer projectId,
			@AuthenticationPrincipal CustomUserDetails loginUser,
			Model model) {
		pageContext.setupProjectDetailPage(model, projectId, loginUser, PageTitleEnum.PROJECT_DETAIL.getTitleKey());
		return TEMPLATE_ROOT + "detail";
	}

}