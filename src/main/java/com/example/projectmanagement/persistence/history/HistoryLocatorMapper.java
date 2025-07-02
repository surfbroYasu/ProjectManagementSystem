package com.example.projectmanagement.persistence.history;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;
import com.example.projectmanagement.history.datastructures.entity.UpdateHistoryEntity;

@Mapper
public interface HistoryLocatorMapper {

	/**
	 * HistoryGroupのIdからHistoryGroupを見つけます。
	 * @param id
	 * @return
	 */
	public HistoryGroupEntity findHistoryGroupById(int id);
	
	/**
	 * HistoryGroupのIdリストからHistoryGroupsを見つけリストを返却します。
	 * @param list<Integer> idList
	 * @return list<HisttoryGroypEntity>
	 */
	public List<HistoryGroupEntity> findHistoryGroupByIdList(List<Integer>list);
	
	
	public List<UpdateHistoryEntity> getLastFiveUpdates(int groupId);
	
	public List<UpdateHistoryEntity> getAllUpdates(int groupId);
	
}