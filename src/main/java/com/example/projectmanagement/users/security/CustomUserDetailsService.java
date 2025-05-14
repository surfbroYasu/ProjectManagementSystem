package com.example.projectmanagement.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.persistence.users.UserMapper;
import com.example.projectmanagement.users.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
        }
        return new CustomUserDetails(user);
    }
}
