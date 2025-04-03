package com.example.projectmanagement.persistence.users;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.modules.users.domain.User;

@Mapper
public interface UserMapper {
	
    User findByUsername(String username);
    
    void insertUser(User user);
}
