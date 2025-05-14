package com.example.projectmanagement.modules.databases.validation;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ColumnStructureValidatorFactory {
    private final Map<String, ColumnStructureValidator> validator;

    public ColumnStructureValidatorFactory(Map<String, ColumnStructureValidator> validators) {
        this.validator = validators;
    }

    public ColumnStructureValidator getValidator(String dbms) {
        return validator.getOrDefault(dbms.toLowerCase(), validator.get("mariadbValidator"));
    }
}
