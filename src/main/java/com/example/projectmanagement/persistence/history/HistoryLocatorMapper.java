package com.example.projectmanagement.persistence.history;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;

@Mapper
public interface HistoryLocatorMapper {

	public HistoryGroupEntity findById(int id);
	public List<HistoryGroupEntity> findByIdList(List<Integer>list);
	
}
