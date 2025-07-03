package com.example.projectmanagement.config;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.projectmanagement.users.datastructure.entity.User;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Profile("dev")
public class DevAutoLoginFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {

			CustomUserDetails dummyUser = new CustomUserDetails(
				    new User(1, "dummy", "ROLE_SYSTEM_DEVELOPER", "Dev", null, "User", "dev@example.com", true)
				);

				UsernamePasswordAuthenticationToken authentication =
				    new UsernamePasswordAuthenticationToken(dummyUser, null, dummyUser.getAuthorities());


			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
		}

		filterChain.doFilter(request, response);
	}
}
