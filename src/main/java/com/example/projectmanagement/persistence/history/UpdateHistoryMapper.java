package com.example.projectmanagement.persistence.history;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.UpdateHistoryEntity;

@Mapper
public interface UpdateHistoryMapper {

	public void insertUpdateHistory(UpdateHistoryEntity entity);
	
	//同じ人から5分以内に同じグループ内でアップデートがあった際は、Logとコメントにアペンドする
	
	public void deleteUpdateHistoryByGroupId(int groupId);
	
	
}
