package com.example.projectmanagement.modules.projects.constance;

import java.util.Locale;

import org.springframework.context.MessageSource;

public enum PageTitleEnum {

	PROJECT_TOP("title.project.top"),
	PROJECT_DETAIL("title.project.detail");

	private final String messageCode;

	PageTitleEnum(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getTitleKey() {
		return messageCode;
	}

	//Thymeleafで使う想定
	public String displayText(MessageSource messageSource, Locale locale) {
		return messageSource.getMessage(messageCode, null, locale);
	}
}
