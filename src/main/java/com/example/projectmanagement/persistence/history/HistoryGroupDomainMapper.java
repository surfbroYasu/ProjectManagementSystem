package com.example.projectmanagement.persistence.history;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;

@Mapper
public interface HistoryGroupDomainMapper {

	public void insertHistoryGroup(HistoryGroupEntity domain);
	public void updateHistoryGroupName(HistoryGroupEntity domain);
	public void deleteHistoryGroup(int id);
	
}
