package com.example.projectmanagement.modules.coding.datastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassField {
    private Integer id;
    private String fieldName;
    private String dataType;
    private Integer classId;

    public ClassField(String fieldName, String dataType, Integer classId) {
		super();
		this.fieldName = fieldName;
		this.dataType = dataType;
		this.classId = classId;
	}
}