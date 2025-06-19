package com.example.projectmanagement.history.services.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;
import com.example.projectmanagement.persistence.history.HistoryMapper;

@Service
public class HistoryGroupService {

	@Autowired
	private HistoryMapper mapper;
	
	public HistoryGroupEntity findHistoryGroupById(int id) {
		return mapper.findById(id);
	}
	
	
	public List<HistoryGroupEntity> findListOfHistoryGroups(List<Integer> ids){
		return mapper.findByIdList(ids);
	}
	
	public void insertHistoryGroup(HistoryGroupEntity domain) {
		mapper.insertHistoryGroup(domain);
	}
	
	public void updateHistoryGroupName(HistoryGroupEntity domain) {
		mapper.updateHistoryGroupName(domain);
	}
	
}
