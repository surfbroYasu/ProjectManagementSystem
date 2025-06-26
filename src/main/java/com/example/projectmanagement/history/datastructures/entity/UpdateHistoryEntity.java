package com.example.projectmanagement.history.datastructures.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateHistoryEntity {
	
	private Integer id;
	private Integer updateBy;
	private String updatedByName;
	private LocalDateTime updateTime;
	private Integer groupId;
	private String log;
	
}
