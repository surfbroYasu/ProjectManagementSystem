package com.example.projectmanagement.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.persistence.users.UserMapper;
import com.example.projectmanagement.users.domain.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        String encoded = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encoded);
        userMapper.insertUser(user);
    }

    public boolean existsByEmail(String email) {
        return userMapper.findByEmail(email) != null;
    }
    

}