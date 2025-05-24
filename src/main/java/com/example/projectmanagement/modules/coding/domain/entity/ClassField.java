package com.example.projectmanagement.modules.coding.domain.entity;

import lombok.Data;

@Data
public class ClassField {
    private Integer id;
    private String fieldName;
    private String dataType;
    private Integer classId;
}