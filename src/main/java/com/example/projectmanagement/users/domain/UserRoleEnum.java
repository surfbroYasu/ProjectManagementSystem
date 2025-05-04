package com.example.projectmanagement.users.domain;

public enum UserRoleEnum {
	
	ROLE_ENTERPRISE_ADMIN, 
	ROLE_ENTERPRISE_DEVELOPER, 
	ROLE_PERSONAL_USER, 
	ROLE_CLIENT_UPLOAD_ONLY,
	ROLE_SYSTEM_DEVELOPER;

	public String getAuthority() {
		return this.name();
	}

}
