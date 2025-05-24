package com.example.projectmanagement.modules.projects.datastructure.model;

import java.util.Locale;

import org.springframework.context.MessageSource;

public enum ProjectDevRoleEnum {
    DEV("label.project.devrole.dev"),
    MANAGER("label.project.devrole.manager"),
    TESTER("label.project.devrole.tester"),
    DESIGNER("label.project.devrole.designer");

    private final String messageCode;

    ProjectDevRoleEnum(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getLabel(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage(messageCode, null, locale);
    }
}
