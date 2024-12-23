package com.golod.buildingmaterialscalculator.service;

import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;
import com.golod.buildingmaterialscalculator.service.validation.UserInputHandler;

import com.golod.buildingmaterialscalculator.service.validation.UserValidator;
import java.util.List;
import java.util.Optional;

public class CalculationService {
  private static final String MATERIALS_FILE_PATH = "data/materials.json";

  private static List<Material> materials;

  static {
    // Читання даних з файлів
    materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);
  }

  public void calculateCement() {
    double area = new UserInputHandler().getDoubleInput("Введіть площу (м²): ");
    double cement = calculateCementAmount(area);
    System.out.printf("Необхідна кількість цементної суміші: %.2f одиниць\n", cement);
  }

  public void calculateWallMaterials() {
    System.out.println("Доступні матеріали:");
    materials.forEach(material ->
        System.out.printf("UUID: %s, Назва: %s\n", material.getId(), material.getName())
    );

    String materialUuid = new UserInputHandler().getStringInput("Введіть UUID матеріалу: ");
    if (!UserValidator.isValidUUID(materialUuid)) {
      System.out.println("Некоректний формат ID. Спробуйте знову.");
    }
    double area = new UserInputHandler().getDoubleInput("Введіть площу (м²): ");
    double perimeter = new UserInputHandler().getDoubleInput("Введіть периметр (м): ");

    Optional<Material> selectedMaterial = materials.stream()
        .filter(material -> material.getId().toString().equals(materialUuid))
        .findFirst();

    if (selectedMaterial.isPresent()) {
      Material material = selectedMaterial.get();
      double quantity = calculateMaterialQuantity(area, perimeter, material);
      System.out.printf("Матеріал: %s, Необхідна кількість: %.2f одиниць\n", material.getName(), quantity);
    } else {
      System.out.println("Матеріал з таким UUID не знайдено.");
    }
  }

  public void calculateRoofingMaterial() {
    double roofArea = new UserInputHandler().getDoubleInput("Введіть площу даху (м²): ");
    double roofing = calculateRoofingAmount(roofArea);
    System.out.printf("Необхідна кількість покрівельного матеріалу: %.2f одиниць\n", roofing);
  }

  public void calculateFlooring() {
    double area = new UserInputHandler().getDoubleInput("Введіть площу (м²): ");
    double flooring = calculateFlooringAmount(area);
    System.out.printf("Необхідна кількість підлогового покриття: %.2f одиниць\n", flooring);
  }

  public void calculatePlaster() {
    double area = new UserInputHandler().getDoubleInput("Введіть площу стін (м²): ");
    double plaster = calculatePlasterAmount(area);
    System.out.printf("Необхідна кількість штукатурки: %.2f одиниць\n", plaster);
  }

  // Приватні методи для виконання розрахунків
  private double calculateCementAmount(double area) {
    return area * 0.2; // 0.2 одиниці цементу на 1 м²
  }

  private double calculateMaterialQuantity(double area, double perimeter, Material material) {
    return (area * perimeter) / material.getUnitSize();
  }

  private double calculateRoofingAmount(double roofArea) {
    return roofArea * 1.5; // 1.5 одиниці матеріалу на 1 м²
  }

  private double calculateFlooringAmount(double area) {
    return area * 1.2; // 1.2 одиниці матеріалу на 1 м²
  }

  private double calculatePlasterAmount(double area) {
    return area * 0.1; // 0.1 одиниці штукатурки на 1 м²
  }
}
