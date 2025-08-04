package com.example.projectmanagement.modules.projects.constance;

import java.util.Locale;

import org.springframework.context.MessageSource;

public enum PermissionLevelEnum {
	OWNER("label.project.permission.owner"), 
	GUARDIAN("label.project.permission.guardian"), 
	MEMBER("label.project.permission.member"), 
	OBSERVER("label.project.permission.observer");

	private final String messageCode;

	PermissionLevelEnum(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageKey() {
		return messageCode;
	}

	public String displayLabel(MessageSource messageSource, Locale locale) {
		return messageSource.getMessage(messageCode, null, locale);
	}
}
