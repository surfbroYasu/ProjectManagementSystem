package com.example.projectmanagement.users.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.projectmanagement.users.domain.User;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    /*
     * ユーザーネームを利用した照合をemailで行う
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
    
    public Map<String, String> getFullNameInMap() {
    	
    	  Map<String, String> nameMap = new HashMap<>();
    	  nameMap.put("firstName", user.getFirstName());
    	  nameMap.put("middleName", user.getMiddleName());
    	  nameMap.put("lastName", user.getLastName());
    	  
    	  return nameMap;
    }
}
