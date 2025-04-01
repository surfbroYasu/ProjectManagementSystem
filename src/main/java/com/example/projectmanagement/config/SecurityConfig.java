package com.example.projectmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // 全リクエストを許可
            )
            .csrf(csrf -> csrf.disable())  // CSRF無効（POSTテスト用など）
            .formLogin(login -> login.disable()) // ログイン画面も無効
            .httpBasic(basic -> basic.disable()); // Basic認証も無効

        return http.build();
    }
}
