package com.example.projectmanagement.persistence.history;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.history.datastructures.entity.HistoryCommentEntity;

/**
 * このクラスは履歴機能のコメントのデータ操作（コメントのドメイン操作、コメントの取得）を行う
 */
@Mapper
public interface HistoryCommentMapper {

	public void insertComment(HistoryCommentEntity comment);
	public void updateComment(HistoryCommentEntity comment);
	public void deleteComment(int id);
	
	public List<HistoryCommentEntity> findCommentsByHistoryGroup(int groupId);
}
