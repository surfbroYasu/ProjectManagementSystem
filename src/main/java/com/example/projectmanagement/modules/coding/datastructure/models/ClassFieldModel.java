package com.example.projectmanagement.modules.coding.datastructure.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassFieldModel {

    private Long id;
    private String fieldName;
    private String dataType;
    private Integer classId;
}
