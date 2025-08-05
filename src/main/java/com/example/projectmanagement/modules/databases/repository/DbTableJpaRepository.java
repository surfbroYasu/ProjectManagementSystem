package com.example.projectmanagement.modules.databases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;

public interface DbTableJpaRepository extends JpaRepository<TableInfoEntity, Long> {

    List<TableInfoEntity> findAllByDbInfoIdIn(List<Integer> dbInfoIds); 
    List<TableInfoEntity> findAllByDbInfoId(Integer dbInfoId);     
}
