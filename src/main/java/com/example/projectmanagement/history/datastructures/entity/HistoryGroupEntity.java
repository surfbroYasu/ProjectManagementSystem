package com.example.projectmanagement.history.datastructures.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HistoryGroupEntity {
	private Integer id;
	private String groupName;
	private LocalDate startDate;
	private Integer originalAuthor;
}
