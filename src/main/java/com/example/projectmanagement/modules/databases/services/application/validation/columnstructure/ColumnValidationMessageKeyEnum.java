package com.example.projectmanagement.modules.databases.services.application.validation.columnstructure;

public enum ColumnValidationMessageKeyEnum {

    DEFAULT_TIMESTAMP_NOT_ALLOWED("error.db.col.default.timestamp_not_allowed"),
    DEFAULT_INVALID_BOOLEAN("error.db.col.default.invalid_boolean"),
    DEFAULT_UNSUPPORTED_TYPE("error.db.col.default.unsupported_type"),
    DEFAULT_PARSE_ERROR("error.db.col.default.parse_error"),
    FK_SETNULL_REQUIRES_NULLABLE("error.db.col.fk.setnull_requires_nullable");

    private final String key;

    ColumnValidationMessageKeyEnum(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
