package com.example.projectmanagement.modules.databases.sqlgenerator;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DataTypeResolverFactory {
	private final Map<String, DataTypeResolver> resolvers;

	public DataTypeResolverFactory(Map<String, DataTypeResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public DataTypeResolver getResolver(String dbms) {
		return resolvers.getOrDefault(dbms.toLowerCase(), resolvers.get("mariadb"));
	}
}
