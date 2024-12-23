package com.golod.buildingmaterialscalculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.service.util.FileUtil;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

import com.golod.buildingmaterialscalculator.service.validation.MaterialValidator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaterialService {

  private static final String MATERIALS_FILE_PATH = "data/materials.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json";
  private static List<Material> materials;
  private static List<Category> categories;

  static {
    materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);
    categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
  }

  public static void main(String[] args) {
    displayMaterials(materials);
  }

  public static void displayMaterials(List<Material> materials) {
    if (materials.isEmpty()) {
      System.out.println("Список матеріалів порожній.");
      return;
    }

    materials.forEach(material -> {
      System.out.println("ID: " + material.getId());
      System.out.println("Назва: " + material.getName());
      System.out.println("Одиниця виміру: " + material.getUnit());
      System.out.println("Ціна за одиницю: " + material.getUnitPrice());
      System.out.println("Розмір одиниці: " + material.getUnitSize());
      Category category = findCategoryById(material.getCategory().getId());
      System.out.println("Категорія: " + (category != null ? category.getName() : "Категорія не знайдена"));
      System.out.println(); // Порожній рядок для кращої читабельності
    });
  }

  public static Category findCategoryById(UUID categoryId) {
    return categories.stream()
        .filter(category -> category.getId().equals(categoryId))
        .findFirst()
        .orElse(null);
  }
  public static void addMaterial() {
    List<Material> materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);

    Scanner scanner = new Scanner(System.in);
    Material newMaterial = new Material();
    newMaterial.setId(UUID.randomUUID()); // Генеруємо унікальний ID для нового матеріалу

    // Введення та валідація назви
    while (true) {
      System.out.print("Введіть назву матеріалу: ");
      String newMaterialName = scanner.nextLine();
      newMaterial.setName(newMaterialName);

      List<String> errors = MaterialValidator.validate(newMaterial);
      List<String> nameErrors = errors.stream()
          .filter(error -> error.contains("Назва матеріалу"))
          .collect(Collectors.toList());
      if (nameErrors.isEmpty()) {
        break;
      } else {
        nameErrors.forEach(System.out::println); // Виводимо всі помилки для назви
      }
    }

    // Введення та валідація одиниці виміру
    while (true) {
      System.out.print("Введіть одиницю виміру (наприклад, 'кг', 'м2', 'шт'): ");
      String newUnit = scanner.nextLine();
      newMaterial.setUnit(newUnit);

      List<String> errors = MaterialValidator.validate(newMaterial);
      List<String> unitErrors = errors.stream()
          .filter(error -> error.contains("Одиниця виміру"))
          .collect(Collectors.toList());
      if (unitErrors.isEmpty()) {
        break;
      } else {
        unitErrors.forEach(System.out::println); // Виводимо всі помилки для одиниці виміру
      }
    }

    // Введення та валідація ціни
    while (true) {
      System.out.print("Введіть ціну матеріалу: ");
      String newUnitPrice = scanner.nextLine();
      try {
        newMaterial.setUnitPrice(Double.parseDouble(newUnitPrice));

        List<String> errors = MaterialValidator.validate(newMaterial);
        List<String> priceErrors = errors.stream()
            .filter(error -> error.contains("Ціна за одиницю"))
            .collect(Collectors.toList());
        if (priceErrors.isEmpty()) {
          break;
        } else {
          priceErrors.forEach(System.out::println); // Виводимо всі помилки для ціни
        }
      } catch (NumberFormatException e) {
        System.out.println("Ціна повинна бути числом. Спробуйте знову.");
      }
    }

    // Введення та валідація розміру одиниці
    while (true) {
      System.out.print("Введіть розмір одиниці (наприклад, для блоків): ");
      String newUnitSize = scanner.nextLine();
      try {
        newMaterial.setUnitSize(Double.parseDouble(newUnitSize));

        List<String> errors = MaterialValidator.validate(newMaterial);
        List<String> unitSizeErrors = errors.stream()
            .filter(error -> error.contains("Розмір одиниці"))
            .collect(Collectors.toList());
        if (unitSizeErrors.isEmpty()) {
          break;
        } else {
          unitSizeErrors.forEach(System.out::println); // Виводимо всі помилки для розміру одиниці
        }
      } catch (NumberFormatException e) {
        System.out.println("Розмір одиниці повинен бути числом. Спробуйте знову.");
      }
    }

    // Введення та валідація UUID категорії
    while (true) {
      System.out.print("Введіть UUID категорії матеріалу: ");
      String categoryIdInput = scanner.nextLine();
      try {
        UUID categoryId = UUID.fromString(categoryIdInput); // Перетворюємо введення на UUID
        Category category = findCategoryById(categoryId);

        if (category != null) {
          newMaterial.setCategory(category);
          break;
        } else {
          System.out.println("Категорія з таким UUID не знайдена.");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Невірний формат UUID. Спробуйте знову.");
      }
    }

    // Додаємо новий матеріал до списку та зберігаємо
    materials.add(newMaterial);
    FileUtil.saveToFile(MATERIALS_FILE_PATH, materials);
    System.out.println("Матеріал успішно додано.");
  }

  private static void saveMaterialsToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(MATERIALS_FILE_PATH), materials);
    } catch (IOException e) {
      System.err.println("Помилка при збереженні матеріалів у файл JSON: " + e.getMessage());
    }
  }
}
