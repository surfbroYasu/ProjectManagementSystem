package com.example.projectmanagement.modules.projects.services.application.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.application.context.ApplicationContextService;
import com.example.projectmanagement.modules.projects.constance.ModelAttributes;
import com.example.projectmanagement.modules.projects.constance.PermissionLevelEnum;
import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.datastructure.entity.ProjectEntity;
import com.example.projectmanagement.modules.projects.repository.ProjectJpaRepository;
import com.example.projectmanagement.modules.projects.services.application.ProjectDevAuthService;
import com.example.projectmanagement.persistence.modules.projects.ProjectMapper;
import com.example.projectmanagement.users.services.application.security.CustomUserDetails;

@Service("project")
public class ProjectViewContextService extends ApplicationContextService {

	@Autowired
	private ProjectMapper mapper;

	//	MyBatisで取得する
	public List<ProjectDtoRecord> getProjectDtoListByUserId(int userId) {
		return mapper.getAllProjectsByUser(userId)
				.stream().map(this::convertToDto)
				.toList();
	}

	@Autowired
	private ProjectJpaRepository projectJpaRepo;

	@Autowired
	private ProjectDevAuthService devAuth;

	/**
	 * 
	 *	 [[ モデル一覧 ]] <br/>
	 * 
	 * project： プロジェクト詳細　ProjectDtoRecord
	 * 
	 * @param model
	 * @param projectId
	 */
	public ProjectEntity setProjectToModel(Model model, Integer projectId) {
		return projectJpaRepo.findById(projectId)
				.map(entity -> {
					model.addAttribute(ModelAttributes.PROJECT, convertToDto(entity));
					return entity;
				})
				.orElseGet(() -> {
					model.addAttribute(ModelAttributes.PROJECT_NOT_FOUND, true);
					return null;
				});
	}
	
	
	/**
	 * プロジェクトの編集権限の有無（boolean）をモデルに追加
	 * @param model
	 * @param loginUser
	 * @param projectId
	 */
	public void projectSettingAuth(Model model, CustomUserDetails loginUser, Integer projectId) {
		
		model.addAttribute(ModelAttributes.PROJECT_SETTING_ALLOWED, devAuth.isSuperviseryPosition(projectId, loginUser.getUserId()));
		
	}

	/**
	 * パーミッションレベル(actual value)をモデルに追加
	 * {@link }
	 * 
	 * @param model
	 * @param loginUser
	 * @param projectId
	 */
	public void setPermissionLevel(Model model, CustomUserDetails loginUser, Integer projectId) {
		PermissionLevelEnum permissionLevel = devAuth.getProjectPermissionLevel(projectId, loginUser.getUserId());
		model.addAttribute(ModelAttributes.PERMISSION_LEVEL, permissionLevel.getMessageKey());
	}

	private ProjectDtoRecord convertToDto(ProjectEntity entity) {
		return new ProjectDtoRecord(
				entity.getId(),
				entity.getProjectName(),
				entity.getApplicationName(),
				entity.getServerSideLang(),
				entity.getStartDate(),
				entity.getOrganization());
	}

}
