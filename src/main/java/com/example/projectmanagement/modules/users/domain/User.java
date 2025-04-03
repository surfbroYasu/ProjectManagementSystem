package com.example.projectmanagement.modules.users.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String name;
    private String passwordHash;
    private String role;
}
