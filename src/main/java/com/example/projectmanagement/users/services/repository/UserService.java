package com.example.projectmanagement.users.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.users.datastructure.entity.UserEntity;
import com.example.projectmanagement.users.repository.UserJpaRepository;

@Service
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserJpaRepository jpaRepo;

    public void registerUser(UserEntity user) {
        String encoded = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encoded);
        jpaRepo.save(user);
    }

    public boolean existsByEmail(String email) {
    	return jpaRepo.existsByEmail(email);
    }
    
    public void updateUserInfo(UserEntity user) {
        jpaRepo.save(user);
    }
    

}