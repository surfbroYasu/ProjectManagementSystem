package com.example.projectmanagement.modules.projects.datastructure.dto;

import java.time.LocalDate;

import com.example.projectmanagement.organizations.datastructure.entity.OrganizationEntity;

/**
 * プロジェクトの画面描画に使用されるDTOレコード。
 * <p>
 * 各フィールドの内容：
 * <ul>
 *   <li>{@code id} - プロジェクトID</li>
 *   <li>{@code projectName} - プロジェクト名</li>
 *   <li>{@code applicationName} - アプリケーション名（対象システム）</li>
 *   <li>{@code serverSideLang} - 使用するサーバーサイド言語（例：Java, Python）</li>
 *   <li>{@code clientId} - 紐づくクライアントID</li>
 *   <li>{@code startDate} - プロジェクト開始日</li>
 * </ul>
 * </p>
 *
 * @author yasufumimisono
 */
public record ProjectDtoRecord(
		Integer id,
		String projectName,
		String applicationName,
		String serverSideLang,
		LocalDate startDate,
		OrganizationEntity organization) {
}
