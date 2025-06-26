package com.example.projectmanagement.history.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;
import com.example.projectmanagement.persistence.history.HistoryGroupDomainMapper;

/**
 * TODO:
 * mapperとのやりとりやDB保存手前のロジックを実装する
 * おそらくHistoryGroupは変更/削除しない。とりあえずその方針
 * @author Misono
 *
 */
@Service
public class HistoryGroupDomainService {

	@Autowired
	private HistoryGroupDomainMapper mapper;
	
	public void insertHistoryGroup(HistoryGroupEntity entity) {
		mapper.insertHistoryGroup(entity);
	}
	
	
	
}
