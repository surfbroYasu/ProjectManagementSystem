package com.example.projectmanagement.history.datastructures.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistoryCommentEntity {

	private Integer id;
	private Integer historyId;
	private Integer authorId;
	private String comment;
	private LocalDateTime writtenTime;
	private String authorName;
	private String status;
	
}
