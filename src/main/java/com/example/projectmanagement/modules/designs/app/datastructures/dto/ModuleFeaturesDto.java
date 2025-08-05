package com.example.projectmanagement.modules.designs.app.datastructures.dto;

import java.util.List;

public record ModuleFeaturesDto(ModuleDefDto module, List<ApplicationFeatureDto> features) {

}
