package com.example.projectmanagement.modules.coding.langgenerator;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ModelGeneratorFactory {

	private final Map<String, ModelGenerator> generators;
	
	public ModelGeneratorFactory(Map<String, ModelGenerator> generators) {
		this.generators = generators;
	}
	
	public ModelGenerator getGenerator(String lang) {
		return generators.getOrDefault(lang.toLowerCase() + "Model", generators.get("javaModel"));
	}
}
