package com.golod.buildingmaterialscalculator.service.validation;

import com.golod.buildingmaterialscalculator.domain.model.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialValidator {

  // Метод для валідації об'єкта Material
  public static List<String> validate(Material material) {
    List<String> errors = new ArrayList<>();

    // Перевірка ідентифікатора
    if (material.getId() == null) {
      errors.add("Ідентифікатор матеріалу не може бути порожнім.");
    }

    // Перевірка назви матеріалу
    if (material.getName() == null || material.getName().isEmpty()) {
      errors.add("Назва матеріалу не може бути порожньою.");
    }

    // Перевірка одиниці виміру
    if (material.getUnit() == null || material.getUnit().isEmpty()) {
      errors.add("Одиниця виміру не може бути порожньою.");
    }

    // Перевірка ціни за одиницю
    if (material.getUnitPrice() <= 0) {
      errors.add("Ціна за одиницю повинна бути більшою за нуль.");
    }

    // Перевірка розміру одиниці
    if (material.getUnitSize() <= 0) {
      errors.add("Розмір одиниці повинен бути більшим за нуль.");
    }

    // Перевірка категорії
    if (material.getCategory() == null) {
      errors.add("Категорія матеріалу не може бути порожньою.");
    }

    return errors; // Повертає список помилок (якщо є)
  }
}
