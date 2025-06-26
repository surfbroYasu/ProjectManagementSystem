package com.example.projectmanagement.modules.coding.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.coding.services.application.ClassDefContextService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/project/{projectId}/classdef")
public class ClassDefController {

	@Autowired
	private ClassDefContextService contextService;

	private static final String TEMPLATE_ROOT = "contents/coding/classdef";

	@GetMapping("/database-entity/{tableId}")
	public String renderModelGeneratorSetupForm(@PathVariable Integer projectId,
			@PathVariable("tableId") Integer tableId,
			@RequestParam("lang") String lang, Model model) {

		model.addAttribute("title", "title.class_def");
		contextService.setEntityView(model, lang, tableId, "entity");

		return TEMPLATE_ROOT + "/codeBlock";
	}

	@PostMapping("/create/{dataUseType}")
	public String renderModelClassEditor(@PathVariable Integer projectId, @PathVariable String dataUseType,
			@RequestParam String classDefJson, Model model) {
		ObjectMapper mapper = new ObjectMapper();
		ClassDefinitionModel classDef;
		try {
			classDef = mapper.readValue(classDefJson, ClassDefinitionModel.class);
			System.out.println(classDef);
			model.addAttribute("classDef", classDef);
			model.addAttribute("title", "title.class_def");
			contextService.setProjectToModel(model, projectId);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return TEMPLATE_ROOT + "/class_editor";
	}
	
	
	/*
	 * TODO
	 * エンティティーの生成は１個まで。エンティティーをベースにモデルやDTOを作成してもらう
	 * 
	 * エンティティー生成は自動でこちらの決めたDBに適したデータ型で一度登録を行い編集という形をとる
	 * 
	 * エンティティーからDTOやModel、Ｆｏｒｍには変更できないようにする
	 * 
	 * DBカラムが追加されたら自動で追加するか？
	 * 
	 * 
	 * 
	 */
	
	
	/*
	 * DTO,Model
	 * DBカラムが削除された場合はどうするか？
	 * エンティティーとの連携を解除し、クラスは温存する
	 * 後々エンティティが生成されたときに紐づけ連携できるようにする
	 */
}
