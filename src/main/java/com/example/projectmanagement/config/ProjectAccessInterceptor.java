package com.example.projectmanagement.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.projectmanagement.modules.projects.services.ProjectService;
import com.example.projectmanagement.users.domain.UserRoleEnum;
import com.example.projectmanagement.users.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProjectAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private ProjectService projectService;

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

                // システム開発者は常に許可
                if (loginUser.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals(UserRoleEnum.ROLE_SYSTEM_DEVELOPER.name()))) {
                    return true;
                }

                // 所有プロジェクトでなければ403
                if (!projectService.isProjectAccessibleByUser(projectId, loginUser.getUserId())) {
                	response.sendError(403);
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