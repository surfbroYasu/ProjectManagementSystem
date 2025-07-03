package com.example.projectmanagement.history.services.repostitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.history.datastructures.entity.UpdateHistoryEntity;
import com.example.projectmanagement.persistence.history.UpdateHistoryMapper;

@Service
public class UpdateHistoryRepostitoryService {

	@Autowired
	private UpdateHistoryMapper mapper;
	

	//TODO:同じ人から5分以内に同じグループ内でアップデートがあった際は、Logとコメントにアペンドする

	private String appendHistoryLog(String latestLog, String newLog) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n" + latestLog).append(newLog);
		return sb.toString();
	}

	/*
	 * 照合項目
	 * 1. updateBy　（user_idと同一）
	 * 2. sameHistoryGroup
	 * 3. updateTime　>　within the last 5 min
	 */

//	private boolean updateAppendable(UpdateHistoryEntity latestUpdate) {
//		return domainMapper.isRecentlyUpdated(latestUpdate, 5);
//	}
	
	private UpdateHistoryEntity findRecentUpdate(UpdateHistoryEntity lastUpdate) {
		return mapper.findRecentUpdate(lastUpdate, 5);
	}
	
	public void updateHistoryLog(UpdateHistoryEntity entity) {
		UpdateHistoryEntity register = findRecentUpdate(entity);
		
		if (register.getLog().isBlank()) {//新規（5分以内に同じユーザーがログを残していない場合）
			mapper.insertUpdateHistory(entity);
		} else {//5分以内に同じユーザーが同じヒストリーグループに更新をかけた時
			entity.setLog(appendHistoryLog(register.getLog(), entity.getLog()));;
			mapper.updateUpdateHistory(entity);
		}
	}

}
