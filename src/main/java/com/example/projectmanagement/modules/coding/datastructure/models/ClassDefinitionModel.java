package com.example.projectmanagement.modules.coding.datastructure.models;

import java.util.List;

import lombok.Data;

@Data
public class ClassDefinitionModel {
    private Integer id;
    private String className;
    private String classAlias;
    private String description;
    private String dataUseType;
    private Integer historyId;
    private String langage;
    private Integer projectId;
    private List<FieldModel> fields; 
}