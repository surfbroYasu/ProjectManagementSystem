package com.example.projectmanagement.modules.databases.validation;

public class ColumnStructureValidationResult {

    private final boolean valid;
    private final String field;       // ← 追加
    private final String messageKey;
    private final Object[] messageArgs;

    private ColumnStructureValidationResult(boolean valid, String field, String messageKey, Object[] messageArgs) {
        this.valid = valid;
        this.field = field;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    public static ColumnStructureValidationResult ok() {
        return new ColumnStructureValidationResult(true, null, null, null);
    }

    public static ColumnStructureValidationResult error(String field, Object messageKey, Object... args) {
        return new ColumnStructureValidationResult(false, field, messageKey.toString(), args);
    }

    // getter追加
    public boolean isValid() {
        return valid;
    }

    public String getField() {
        return field;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }
}
