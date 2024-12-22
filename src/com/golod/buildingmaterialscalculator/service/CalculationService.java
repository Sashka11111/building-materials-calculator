package com.golod.buildingmaterialscalculator.service;

import com.golod.buildingmaterialscalculator.domain.model.Calculation;
import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.presentation.Application;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

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
    service.displayUserCalculations();
  }

  // Метод для отримання розрахунків для конкретного користувача
  public List<Calculation> getUserCalculations(UUID userId) {
    return calculations.stream()
        .filter(calculation -> calculation.getUserId().equals(userId))  // Фільтруємо за userId
        .collect(Collectors.toList());  // Перетворюємо у список
  }
  // Розрахунок кількості цементної суміші для будівництва (площу помножити на коефіцієнт)
  public static double calculateCement(double area) {
    return area * 0.2; // Приклад: 0.2 одиниці цементної суміші на 1 м²
  }

  // Розрахунок кількості матеріалів для стін (бетонний блок, цегла, газоблок)
  public static List<Material> calculateWallMaterials(double area, double perimeter) {
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
  public static double calculateRoofingMaterial(double roofArea) {
    return roofArea * 1.5;  // Приклад: 1.5 одиниці матеріалу на 1 м² даху
  }

  // Розрахунок кількості підлогового покриття
  public static double calculateFlooring(double area) {
    return area * 1.2; // Приклад: 1.2 одиниці матеріалу на 1 м² підлоги
  }

  // Розрахунок штукатурки для стін
  public static double calculatePlaster(double area) {
    return area * 0.1; // Приклад: 0.1 одиниці штукатурки на 1 м²
  }


  public static void displayUserCalculations() {
    UUID userId = Application.currentUser.getUserId();
    // Фільтруємо розрахунки за userId
    CalculationService service = new CalculationService();
    List<Calculation> userCalculations = service.getUserCalculations(userId);

    if (userCalculations.isEmpty()) {
      System.out.println("Немає розрахунків для цього користувача.");
    } else {
      userCalculations.forEach(calculation -> {
        System.out.println("ID: " + calculation.getId());
        System.out.println("Тип будівлі: " + calculation.getBuildingType());
        System.out.println("Площа: " + calculation.getArea());
        System.out.println("Периметр: " + calculation.getPerimeter());
        System.out.println("Результати:");

        // Виведення результатів, якщо це список Material
        calculation.getResults().forEach(material -> {
          // Використовуємо метод getUnitSize для класу Material
          System.out.println("Матеріал: " + material.getName() + " - Кількість: " + material.getUnitSize());
        });

        System.out.println();
      });
    }
  }
}
