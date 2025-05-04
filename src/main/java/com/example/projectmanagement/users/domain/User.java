package com.example.projectmanagement.users.domain;

import lombok.Data;

@Data
public class User {
	
    private Integer id;
    private String passwordHash;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private Boolean isActive;
    
}
