package com.example.projectmanagement.users.datastructure.enums;

public enum UserRoleEnum {

	//	企業アカウントのアドミン
	ROLE_ENTERPRISE_ADMIN,
	//	企業アカウントの開発ユーザー
	ROLE_ENTERPRISE_DEVELOPER,
	//	一般ユーザー
	ROLE_PERSONAL_USER,
	//	クライアントユーザー（ファイルアップロードのみを許可）
	ROLE_CLIENT_UPLOAD_ONLY,

	//	このアプリケーション開発者用
	ROLE_SYSTEM_DEVELOPER;

	public String getAuthority() {
		return this.name();
	}

}
