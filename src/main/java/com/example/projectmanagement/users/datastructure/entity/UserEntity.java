package com.example.projectmanagement.users.datastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	
	@Id
    private Integer id;
    private String passwordHash;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private Boolean isActive;
    
}
