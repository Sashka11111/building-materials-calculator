package com.golod.buildingmaterialscalculator.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperConfig {
  // Створення налаштованого ObjectMapper
  public static ObjectMapper getConfiguredObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Відступи для форматованого JSON
    return objectMapper;
  }
}

