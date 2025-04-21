package com.example.projectmanagement.modules.databases.sqlgenerator;

public abstract class AbstractSqlSyntaxGenerator implements SqlSyntaxGenerator{

    protected String formatValue(Object value) {
        if (value == null) return "NULL";
        if (value instanceof Number || value instanceof Boolean) return value.toString();
        return "'" + value.toString().replace("'", "''") + "'";
    }
    
}
