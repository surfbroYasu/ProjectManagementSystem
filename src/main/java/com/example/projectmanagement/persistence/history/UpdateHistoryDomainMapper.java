package com.example.projectmanagement.persistence.history;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.UpdateHistoryEntity;

/**
 * update_historiesテーブル専用のデータドメインマッパーです。
 * insert 
 * update
 */
@Mapper
public interface UpdateHistoryDomainMapper {

	/*
	 * 新規登録
	 */
	public void insertUpdateHistory(UpdateHistoryEntity entity);

	/**
	 * 履歴のアップデート(ロジックなし)
	 * 
	 * @param entity
	 */
	public void updateUpdateHistory(UpdateHistoryEntity entity);

	/**
	 * interval に指定された分数以内に同じユーザーから更新履歴がある場合に deleteUpdateHistoryByGroupIdを返す
	 * 
	 * @param entity
	 * @param interval
	 * @return　UpdateHistoryEntity　latestUpdateWithinIntervalMin
	 */
	public UpdateHistoryEntity findRecentUpdate(UpdateHistoryEntity entity, int interval);

	public void deleteUpdateHistoryByGroupId(int groupId);

}
