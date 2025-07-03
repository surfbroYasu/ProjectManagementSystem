package com.example.projectmanagement.persistence.users;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.users.datastructure.entity.User;

@Mapper
public interface UserMapper {
	
    User findByEmail(String email);
    
    void insertUser(User user);
    
    void updateUserInfo(User user);
}
