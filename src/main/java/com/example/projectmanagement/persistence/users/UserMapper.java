package com.example.projectmanagement.persistence.users;

import org.apache.ibatis.annotations.Mapper;

import com.example.projectmanagement.users.datastructure.entity.UserEntity;

@Mapper
public interface UserMapper {
	
    UserEntity findByEmail(String email);
    
    void insertUser(UserEntity user);
    
    void updateUserInfo(UserEntity user);
}
