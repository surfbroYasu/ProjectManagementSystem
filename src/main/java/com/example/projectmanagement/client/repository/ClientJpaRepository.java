package com.example.projectmanagement.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.client.datastructures.entities.ClientEntity;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Integer> {

}
