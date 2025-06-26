package com.example.projectmanagement.history.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;
import com.example.projectmanagement.persistence.history.HistoryLocatorMapper;

@Service
public class HistoryLocatorService {

	@Autowired
	private HistoryLocatorMapper mapper;
	
	public HistoryGroupEntity findHistoryGroupById(int id) {
		return mapper.findById(id);
	}
	
	
	public List<HistoryGroupEntity> findListOfHistoryGroups(List<Integer> ids){
		return mapper.findByIdList(ids);
	}
	
	
}
