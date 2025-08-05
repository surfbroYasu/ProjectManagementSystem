package com.example.projectmanagement.modules.databases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;

public interface DbColumnJpaRepository extends JpaRepository<TableColumnEntity, Long> {

	public List<TableColumnEntity> findAllBytableInfoIdIn(List<Long> tableIds);
	public List<TableColumnEntity> findAllBytableInfoId(Long tableId);
	
}
