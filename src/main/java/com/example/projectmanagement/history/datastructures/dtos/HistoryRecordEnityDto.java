package com.example.projectmanagement.history.datastructures.dtos;

import java.util.List;

import com.example.projectmanagement.history.datastructures.entity.HistoryGroupEntity;
import com.example.projectmanagement.history.datastructures.entity.UpdateHistoryEntity;

import lombok.Data;

/**
 * このクラスは、
 * 履歴グループとそのアップデート記録（直近５個まで）を同時に内包しデータトランスファーを簡単にするためのクラスである。
 * 画面描画やDBからの取得には向かない。
 * 
 * @author Misono
 *
 */
@Data
public class HistoryRecordEnityDto {

	private HistoryGroupEntity historyGroup;
	private List<UpdateHistoryEntity> updates;
	
}
