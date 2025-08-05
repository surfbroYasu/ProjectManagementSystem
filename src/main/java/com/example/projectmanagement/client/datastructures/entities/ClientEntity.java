package com.example.projectmanagement.client.datastructures.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="clients")
public class ClientEntity {

	@Id
	private Integer id;
	private String name;
	private String repName;
	private String phone;
	private String note;
	
}
