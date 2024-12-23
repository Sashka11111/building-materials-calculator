package com.golod.buildingmaterialscalculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

import com.golod.buildingmaterialscalculator.service.validation.MaterialValidator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
    Scanner scanner = new Scanner(System.in);
    System.out.println("Додавання нового матеріалу");

    String name = getValidatedName(scanner);
    String unit = getValidatedUnit(scanner);
    double unitPrice = getValidatedUnitPrice(scanner);
    double unitSize = getValidatedUnitSize(scanner);
    String categoryId = getValidatedCategoryId(scanner);

    // Генерація унікального UUID
    UUID materialId = UUID.randomUUID();

    // Пошук категорії за ID
    Category category = findCategoryById(UUID.fromString(categoryId));
    if (category == null) {
      System.out.println("Категорію з таким ID не знайдено. Скасування операції.");
      return;
    }

    // Створення нового матеріалу
    Material newMaterial = new Material(materialId, name, unit, unitPrice, unitSize, category);

    // Виконання валідації
    List<String> validationErrors = MaterialValidator.validate(newMaterial);

    // Якщо є помилки валідації, вивести їх
    if (!validationErrors.isEmpty()) {
      validationErrors.forEach(System.out::println);
      return;
    }

    // Якщо матеріал валідний, додаємо його до списку та зберігаємо
    materials.add(newMaterial);
    saveMaterialsToJson();

    System.out.println("Новий матеріал додано успішно");
  }

  private static String getValidatedName(Scanner scanner) {
    System.out.print("Введіть назву матеріалу: ");
    return scanner.nextLine();
  }

  private static String getValidatedUnit(Scanner scanner) {
    System.out.print("Введіть одиницю виміру матеріалу: ");
    return scanner.nextLine();
  }

  private static double getValidatedUnitPrice(Scanner scanner) {
    double unitPrice;
    while (true) {
      System.out.print("Введіть ціну за одиницю: ");
      String input = scanner.nextLine();
      try {
        unitPrice = Double.parseDouble(input);
        if (unitPrice <= 0) {
          System.out.println("Ціна за одиницю повинна бути більшою за нуль.");
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Введіть правильну ціну.");
      }
    }
    return unitPrice;
  }

  private static double getValidatedUnitSize(Scanner scanner) {
    double unitSize;
    while (true) {
      System.out.print("Введіть розмір одиниці: ");
      String input = scanner.nextLine();
      try {
        unitSize = Double.parseDouble(input);
        if (unitSize <= 0) {
          System.out.println("Розмір одиниці повинен бути більшим за нуль.");
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Введіть правильний розмір одиниці.");
      }
    }
    return unitSize;
  }

  private static String getValidatedCategoryId(Scanner scanner) {
    System.out.print("Введіть ID категорії матеріалу: ");
    return scanner.nextLine();
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
