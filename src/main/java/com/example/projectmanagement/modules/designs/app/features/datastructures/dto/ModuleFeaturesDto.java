package com.example.projectmanagement.modules.designs.app.features.datastructures.dto;

import java.util.List;

import com.example.projectmanagement.modules.designs.app.modules.datastructures.dto.ModuleDefDto;

public record ModuleFeaturesDto(ModuleDefDto module, List<ApplicationFeatureDto> features) {

}
