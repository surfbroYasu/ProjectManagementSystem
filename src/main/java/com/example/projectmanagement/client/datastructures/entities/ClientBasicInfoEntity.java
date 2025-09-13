package com.example.projectmanagement.client.datastructures.entities;

import com.example.projectmanagement.organizations.datastructure.entity.OrganizationEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "client_basic_info")
public class ClientBasicInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String phone;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
	private OrganizationEntity organization;

	//	TODO
	//	国際電話対応にするか検討する！
}
