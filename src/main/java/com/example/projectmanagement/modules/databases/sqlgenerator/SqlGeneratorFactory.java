package com.example.projectmanagement.modules.databases.sqlgenerator;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SqlGeneratorFactory {

    private final Map<String, SqlSyntaxGenerator> generators;

    public SqlGeneratorFactory(Map<String, SqlSyntaxGenerator> generators) {
        this.generators = generators;
    }

    public SqlSyntaxGenerator getGenerator(String dbms) {
        return generators.getOrDefault(dbms.toLowerCase(), generators.get("mariadbSyntax"));
    }
}
