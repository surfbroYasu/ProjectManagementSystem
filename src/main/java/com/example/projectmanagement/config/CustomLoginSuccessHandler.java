package com.example.projectmanagement.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.projectmanagement.users.security.CustomUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication)
			throws IOException, ServletException {

		CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();
		Map<String, String> nameMap = loginUser.getFullNameInMap();

		boolean hasFirst = nameMap.get("firstName") != null && !nameMap.get("firstName").isBlank();
		boolean hasLast = nameMap.get("lastName") != null && !nameMap.get("lastName").isBlank();

		if (hasFirst && hasLast) {
			response.sendRedirect("/");
		} else {
			response.sendRedirect("/user/setup");
		}
	}

}
