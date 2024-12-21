package com.golod.buildingmaterialscalculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.golod.buildingmaterialscalculator.domain.model.Calculation;
import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalculationService {

  private static final String CALCULATIONS_FILE_PATH = "data/calculations.json";
  private static final String MATERIALS_FILE_PATH = "data/materials.json";

  private static List<Material> materials;
  private static List<Calculation> calculations;

  static {
    // Читання даних з файлів
    materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);
    calculations = JsonDataReader.modelDataJsonReader(CALCULATIONS_FILE_PATH, Calculation[].class);
  }

  public static void main(String[] args) {
    // Тестування
    CalculationService service = new CalculationService();
    service.displayAllCalculations();
  }

  // Метод для обчислення матеріалів на основі площі, периметра та площі даху
//  public static Calculation calculateMaterials(String userId, String buildingType, double area, double perimeter, double roofArea) {
//    List<Material> resultCategories = calculateMaterialsForBuilding(area, perimeter, roofArea);
//
//    // Створення нового об'єкта Calculation
//    String id = UUID.randomUUID().toString();
//    Calculation calculation = new Calculation(id, userId, buildingType, area, perimeter, roofArea);
//
//    // Додавання розрахунку до списку
//    calculations.add(calculation);
//
//    // Збереження результатів у файл
//    saveCalculationsToJson();
//
//    return calculation;
//  }

  // Метод для обчислення матеріалів для будівлі
  private static List<Material> calculateMaterialsForBuilding(double area, double perimeter, double roofArea) {
    // Логіка для розрахунку необхідної кількості матеріалів

    // Розрахунок цементної суміші
    double cementRequired = calculateCement(area);

    // Розрахунок кількості матеріалів для стін (бетонний блок, цегла, газоблок)
    List<Material> wallMaterials = calculateWallMaterials(area, perimeter);

    // Розрахунок покрівельного матеріалу
    double roofingMaterialRequired = calculateRoofingMaterial(roofArea);

    // Розрахунок підлогового покриття
    double flooringRequired = calculateFlooring(area);

    // Розрахунок штукатурки для стін
    double plasterRequired = calculatePlaster(area);

    // Об'єднання всіх матеріалів у список результатів
    return materials.stream()
        .map(material -> {
          // Розподіляємо матеріали по їх типах
          if ("Cement".equals(material.getName())) {
            material.setUnitSize(cementRequired);  // Встановлюємо необхідну кількість цементу
          } else if ("Wall Material".equals(material.getName())) {
            material.setUnitSize(wallMaterials.size());  // Встановлюємо кількість стінних матеріалів
          } else if ("Roofing".equals(material.getName())) {
            material.setUnitSize(roofingMaterialRequired);  // Встановлюємо кількість покрівельних матеріалів
          } else if ("Flooring".equals(material.getName())) {
            material.setUnitSize(flooringRequired);  // Встановлюємо кількість підлогового покриття
          } else if ("Plaster".equals(material.getName())) {
            material.setUnitSize(plasterRequired);  // Встановлюємо кількість штукатурки
          }
          return material;
        })
        .collect(Collectors.toList());  // Повертайте оновлений список матеріалів
  }

  // Розрахунок кількості цементної суміші для будівництва (площу помножити на коефіцієнт)
  private static double calculateCement(double area) {
    return area * 0.2; // Приклад: 0.2 одиниці цементної суміші на 1 м²
  }

  // Розрахунок кількості матеріалів для стін (бетонний блок, цегла, газоблок)
  private static List<Material> calculateWallMaterials(double area, double perimeter) {
    // Наприклад, можна використовувати різні матеріали в залежності від розміру
    return materials.stream()
        .filter(material -> material.getCategory().getName().equals("Wall Material"))
        .map(material -> {
          double quantity = (area * perimeter) / material.getUnitSize();  // Розмір одиниці матеріалу
          material.setUnitSize(quantity);  // Встановлюємо кількість матеріалу
          return material;
        })
        .collect(Collectors.toList());
  }

  // Розрахунок кількості покрівельного матеріалу
  private static double calculateRoofingMaterial(double roofArea) {
    return roofArea * 1.5;  // Приклад: 1.5 одиниці матеріалу на 1 м² даху
  }

  // Розрахунок кількості підлогового покриття
  private static double calculateFlooring(double area) {
    return area * 1.2; // Приклад: 1.2 одиниці матеріалу на 1 м² підлоги
  }

  // Розрахунок штукатурки для стін
  private static double calculatePlaster(double area) {
    return area * 0.1; // Приклад: 0.1 одиниці штукатурки на 1 м²
  }

  // Збереження розрахунків у файл JSON
  private static void saveCalculationsToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(CALCULATIONS_FILE_PATH), calculations);
    } catch (IOException e) {
      System.err.println("Помилка при збереженні розрахунків у файл JSON: " + e.getMessage());
    }
  }

  // Виведення всіх розрахунків
  public static void displayAllCalculations() {
    if (calculations.isEmpty()) {
      System.out.println("Немає розрахунків.");
    } else {
      calculations.forEach(calculation -> {
        System.out.println("ID: " + calculation.getId());
        System.out.println("Тип будівлі: " + calculation.getBuildingType());
        System.out.println("Площа: " + calculation.getArea());
        System.out.println("Периметр: " + calculation.getPerimeter());
        System.out.println("Результати:");
//        calculation.getResults().forEach(material -> {
//          System.out.println("Матеріал: " + material.getName() + " - Кількість: " + material.getUnitSize());
//        });
        System.out.println();
      });
    }
  }
}
