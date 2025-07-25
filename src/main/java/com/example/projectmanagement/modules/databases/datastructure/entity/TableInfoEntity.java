package com.example.projectmanagement.modules.databases.datastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "table_info")
public class TableInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer dbInfoId;
	private String tableName;
	private String tableAlias;
	private Integer historyId;
	
}
