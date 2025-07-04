package com.example.projectmanagement.modules.coding.datastructure.models;

import lombok.Data;

@Data
public class FieldModel {
    private Integer id;
    private String fieldName;
    private String dataType;
    private Integer tableColId;
    private Integer classId;
}
