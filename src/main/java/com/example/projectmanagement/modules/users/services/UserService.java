package com.example.projectmanagement.modules.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.users.domain.User;
import com.example.projectmanagement.persistence.users.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        String encoded = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encoded);
        user.setRole("USER");
        userMapper.insertUser(user);
    }

    public boolean existsByUsername(String username) {
        return userMapper.findByUsername(username) != null;
    }

}
