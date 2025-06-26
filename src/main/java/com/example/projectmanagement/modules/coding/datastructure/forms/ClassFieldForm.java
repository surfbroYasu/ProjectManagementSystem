package com.example.projectmanagement.modules.coding.datastructure.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassFieldForm {
    private Integer id;
    @NotBlank
    private String fieldName;
    private String dataType;
    private Integer classId;
}