package com.example.projectmanagement.modules.coding.datastructure.models;

import lombok.Data;


/**
 * フィールドエンティティにDBカラムIDを追加したクラス。
 * これによりカラムとの連動性がよくなる
 * 
 * @author yasufumimisono
 *
 */
@Data
public class EntityFieldModel {
    private Integer id;
    private String fieldName;
    private String dataType;
    private Integer tableColId;
    private Integer classId;
}
