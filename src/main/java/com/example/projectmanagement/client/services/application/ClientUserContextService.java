package com.example.projectmanagement.client.services.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.projectmanagement.client.datastructures.dtos.ClientUserDtoRecord;
import com.example.projectmanagement.client.datastructures.entities.ClientUserInfoEntity;
import com.example.projectmanagement.client.repository.ClientUserJpaRepository;
import com.example.projectmanagement.modules.projects.datastructure.dto.ProjectDtoRecord;
import com.example.projectmanagement.modules.projects.services.application.context.ProjectViewContextService;

@Service("clientUser")
public class ClientUserContextService extends ProjectViewContextService {

	@Autowired
	private ClientUserJpaRepository jpaRepo;

	/**
	 * クライアント担当者の一覧情報をモデルに設定します（画面描画用）。
	 *
	 * <hr/>
	 * <strong>モデル属性:</strong><br/>
	 * ▸ {@code clientUsers} : クライアント担当者一覧  ：
	 * {@link ClientUserDtoRecord} — 担当者1人分の情報を表すDTOのリスト<br/>
	 * ▸ {@code title} : ページタイトル  
	 * ：{@code String} — 表示用のページタイトル文字列<br/>
	 * ▸ {@code project} : プロジェクト詳細  
	 * ：{@link ProjectDtoRecord} — 表示中のプロジェクトの基本情報
	 * <hr/>
	 *
	 * @param model     モデルに詰めるオブジェクト（ビューで使用）
	 * @param projectId 表示対象のプロジェクトID
	 * @param clientId  表示対象のクライアントID
	 * @param titleProp タイトルとして表示する文字列、またはプロパティキー
	 */
	public void setClientUserListContext(Model model, int projectId, int clientId, String titleProp) {
		setPageTitle(model, titleProp);
		setProjectToModel(model, projectId);

		List<ClientUserInfoEntity> entities = Optional.ofNullable(jpaRepo.findByClientId(clientId))
				.orElse(Collections.emptyList());

		List<ClientUserDtoRecord> dtoList = entities.stream()
				.map(this::convertClientUserEntityToRecord)
				.collect(Collectors.toList());

		model.addAttribute("clientUsers", dtoList);
	}

	/**
	 * クライアント担当者の詳細情報をモデルに設定します。
	 *
	  * <hr/>
	 * <strong>モデル属性:</strong><br/>
	 * ▸ {@code personnel} : クライアント担当者一覧  ：
	 * {@link ClientUserDtoRecord} — クライアント担当者のDTOデータ<br/>
	 * ▸ {@code title} : ページタイトル  
	 * ：{@code String} — 表示用のページタイトル文字列<br/>
	 * ▸ {@code project} : プロジェクト詳細  
	 * ：{@link ProjectDtoRecord} — 表示中のプロジェクトの基本情報
	 * <hr/>
	 * 
	 * @param model Thymeleafなどで使用するModelオブジェクト
	 * @param projectId 対象プロジェクトのID
	 * @param personnelId 対象のクライアント担当者のID
	 * @param title ページに表示するタイトル文字列
	 */

	public void setClientUserDetail(Model model, int projectId, int personnelId, String title) {
		setPageTitle(model, title);
		setProjectToModel(model, projectId);

		jpaRepo.findById(personnelId)
				.map(this::convertClientUserEntityToRecord)
				.ifPresentOrElse(
						record -> model.addAttribute("personnel", record),
						() -> {
							// ユーザーが見つからない場合のビューへの伝達
							model.addAttribute("personnelNotFound", true);
						});
	}

	private ClientUserDtoRecord convertClientUserEntityToRecord(ClientUserInfoEntity entity) {
		return new ClientUserDtoRecord(
				entity.getId(),
				entity.getClientId(),
				entity.getPosition(),
				entity.getNote(),
				entity.getEmail(),
				entity.getPhone(),
				entity.getFirstName(),
				entity.getMiddleName(),
				entity.getLastName());

	}
}
