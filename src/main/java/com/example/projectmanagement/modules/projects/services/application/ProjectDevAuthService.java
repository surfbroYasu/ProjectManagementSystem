package com.example.projectmanagement.modules.projects.services.application;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagement.modules.projects.constance.PermissionLevelEnum;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectDeveloperEntity;
import com.example.projectmanagement.modules.projects.repository.ProjectDeveloperJpaRepository;

@Service
public class ProjectDevAuthService {

	@Autowired
	private ProjectDeveloperJpaRepository devJpa;

	public PermissionLevelEnum getProjectPermissionLevel(int projectId, int userId) {

		ProjectDeveloperEntity devInfo = devJpa.findByUserIdAndProjectId(userId, projectId)
				.orElseThrow(() -> new IllegalArgumentException("User " + userId + " is not assigned to project " + projectId));

		return PermissionLevelEnum.valueOf(devInfo.getPermissionLevel());
	}
	

	public boolean hasEditPermission(int projectId, int userId) {
	    return EDITABLE_LEVELS.contains(getProjectPermissionLevel(projectId, userId));
	}

	public boolean isSuperviseryPosition(int projectId, int userId) {
	    return SUPERVISORY_LEVELS.contains(getProjectPermissionLevel(projectId, userId));
	}


	public boolean isObserverOnly(int projectId, int userId) {
		return getProjectPermissionLevel(projectId, userId) == PermissionLevelEnum.OBSERVER;
	}

	
	private static final EnumSet<PermissionLevelEnum> EDITABLE_LEVELS =
			EnumSet.of(PermissionLevelEnum.OWNER, PermissionLevelEnum.GUARDIAN, PermissionLevelEnum.MEMBER);
	
	private static final EnumSet<PermissionLevelEnum> SUPERVISORY_LEVELS =
			EnumSet.of(PermissionLevelEnum.OWNER, PermissionLevelEnum.GUARDIAN);

}
