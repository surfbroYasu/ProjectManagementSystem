package com.example.projectmanagement.modules.projects.constance;

import java.util.Locale;

import org.springframework.context.MessageSource;

public enum ProjectDevRoleEnum {
    DEV("label.project.devrole.dev"),
    TESTER("label.project.devrole.tester"),
    DESIGNER("label.project.devrole.designer");

    private final String messageCode;

    ProjectDevRoleEnum(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageKey() {
        return messageCode;
    }

    public String displayLabel(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage(messageCode, null, locale);
    }
}
