package com.golod.buildingmaterialscalculator.service.validation;

import com.golod.buildingmaterialscalculator.domain.model.Category;
import java.util.List;
import java.util.UUID;

public class CategoryValidator {

  // Валідація для перевірки правильності формату категорії
  public static boolean isValidCategoryName(String name) {
    return UserValidator.isNotEmpty(name) && name.length() <= 50;
  }

  // Метод для перевірки, чи є категорія з такою назвою
  public static boolean isCategoryNameUnique(List<Category> categories, String name) {
    return categories.stream().noneMatch(category -> category.getName().equalsIgnoreCase(name));
  }

  // Метод для перевірки унікальності ID категорії
  public static boolean isCategoryIdUnique(List<Category> categories, UUID id) {
    return categories.stream().noneMatch(category -> category.getId().equals(id));
  }
}
