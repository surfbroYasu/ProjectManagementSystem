package com.example.projectmanagement.modules.databases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;

public interface DbInfoJpaRepository extends JpaRepository<DBInfoEntity, Integer> {

	List<DBInfoEntity> findAllByProjectId(int projectId);
	
}
