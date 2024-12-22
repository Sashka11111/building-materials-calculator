package com.golod.buildingmaterialscalculator.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.golod.buildingmaterialscalculator.domain.model.Material;

public class JsonDataReader {
  public static <T> List<T> modelDataJsonReader(String filePath, Class<T[]> clazz) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      T[] dataArray = objectMapper.readValue(new File(filePath), clazz);
      return new ArrayList<>(List.of(dataArray));  // Перетворюємо на змінюваний список
    } catch (IOException e) {
      System.out.println("Помилка при читанні JSON: " + e.getMessage());
      return new ArrayList<>(); // Повертаємо порожній список у разі помилки
    }
  }

  public static void main(String[] args) {
    List<Material> materials = modelDataJsonReader("materials.json", Material[].class);
    materials.forEach(System.out::println);  // Вивести всі матеріали
  }
}
