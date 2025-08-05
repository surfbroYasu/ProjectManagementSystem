package com.example.projectmanagement.application.config;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloperEntity;
import com.example.projectmanagement.modules.projects.repository.ProjectDeveloperJpaRepository;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProjectAccessInterceptor implements HandlerInterceptor {


	@Autowired
	private ProjectDeveloperJpaRepository devJpa;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getRequestURI();
		Pattern pattern = Pattern.compile("^/project/(\\d+)(/.*)?$");
		Matcher matcher = pattern.matcher(path);

		if (matcher.find()) {
			try {
				int projectId = Integer.parseInt(matcher.group(1));

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails loginUser)) {
					response.sendRedirect("/login");
					return false;
				}


				// 所有プロジェクトでなければ403
				Optional<ProjectDeveloperEntity> devOpt = devJpa.findByUserIdAndProjectId(loginUser.getUserId(), projectId);

				if (devOpt.isEmpty()) {
					response.sendError(403);
					return false;
				} 
				ProjectDeveloperEntity dev = devOpt.get();
				if (!dev.getIsActiveMember()) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN); 
					return false;
				}


			} catch (Exception e) {
				response.sendRedirect("/error/400");
				return false;
			}
		}

		return true;
	}
}