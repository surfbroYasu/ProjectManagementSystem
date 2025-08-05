package com.example.projectmanagement.modules.coding.services.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefFieldsModel;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassFieldModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClassDefContextHelperService {

	void addJsonModel(Model model, ClassDefFieldsModel classDefModel) {
		try {
			String json = new ObjectMapper().writeValueAsString(classDefModel);
			model.addAttribute("classDefJson", json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public ClassDefFieldsModel convertClassDefEntityToModel(ClassDefinitionEntity classDef,
			List<ClassFieldModel> fields) {
		return new ClassDefFieldsModel(
				classDef.getId(),
				classDef.getClassName(),
				classDef.getClassAlias(),
				classDef.getDescription(),
				classDef.getDataUseType(),
				classDef.getHistoryId(),
				classDef.getLanguage(),
				classDef.getProjectId(),
				fields);
	}

	public ClassFieldModel convertFieldEntityToModel(ClassFieldEntity field) {
		return new ClassFieldModel(
				field.getId(),
				field.getFieldName(),
				field.getDataType(),
				field.getClassId());
	}

}
