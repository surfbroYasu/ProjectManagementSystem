package com.example.projectmanagement.history.services.application.loggenerator;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HistoryLogGeneratorFactory {
    private final Map<String, HistoryLogGenerator> generators;

    public HistoryLogGeneratorFactory(Map<String, HistoryLogGenerator> generators) {
        this.generators = generators;
    }

    public HistoryLogGenerator getGenerator(String naturalLangInitial) {
        return generators.getOrDefault(naturalLangInitial.toLowerCase() + "log", generators.get("enlog"));
    }
}
