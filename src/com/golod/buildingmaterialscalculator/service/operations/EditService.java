package com.golod.buildingmaterialscalculator.service.operations;

import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.service.util.FileUtil;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;
import com.golod.buildingmaterialscalculator.service.validation.MaterialValidator;
import com.golod.buildingmaterialscalculator.service.validation.CategoryValidator;
import com.golod.buildingmaterialscalculator.service.validation.UserValidator;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EditService {

  private static final String MATERIALS_FILE_PATH = "data/materials.json"; // Файл для матеріалів
  private static final String CATEGORIES_FILE_PATH = "data/categories.json"; // Файл для категорій

  // Метод редагування матеріалу
  public static void editMaterial() {
    List<Material> materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);

    if (materials.isEmpty()) {
      System.out.println("Список матеріалів порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    Material material = null;

    // Пошук матеріалу за ID
    while (material == null) {
      System.out.print("Введіть ID матеріалу для редагування: ");
      String inputId = scanner.nextLine();

      if (!UserValidator.isValidUUID(inputId)) {
        System.out.println("Некоректний формат ID. Спробуйте знову.");
        continue;
      }

      UUID materialId = UUID.fromString(inputId);
      material = materials.stream()
          .filter(m -> m.getId().equals(materialId))
          .findFirst()
          .orElse(null);

      if (material == null) {
        System.out.println("Матеріал з таким ID не знайдений. Спробуйте ще раз.");
      }
    }

    // Редагування матеріалу з перевіркою кожного поля
    while (true) {
      System.out.print("Введіть нову назву матеріалу: ");
      String newMaterialName = scanner.nextLine();
      material.setName(newMaterialName);

      List<String> nameErrors = MaterialValidator.validate(material);
      if (nameErrors.isEmpty()) {
        break;
      } else {
        System.out.println("Знайдено помилки у введеній назві:");
        nameErrors.forEach(error -> System.out.println("- " + error));
      }
    }

    while (true) {
      System.out.print("Введіть нову одиницю виміру (наприклад, 'кг', 'м2', 'шт'): ");
      String newUnit = scanner.nextLine();
      material.setUnit(newUnit);

      List<String> unitErrors = MaterialValidator.validate(material);
      if (unitErrors.isEmpty()) {
        break;
      } else {
        System.out.println("Знайдено помилки у введеній одиниці виміру:");
        unitErrors.forEach(error -> System.out.println("- " + error));
      }
    }

    while (true) {
      System.out.print("Введіть нову ціну матеріалу: ");
      String newUnitPrice = scanner.nextLine();
      try {
        material.setUnitPrice(Double.parseDouble(newUnitPrice));

        List<String> priceErrors = MaterialValidator.validate(material);
        if (priceErrors.isEmpty()) {
          break;
        } else {
          System.out.println("Знайдено помилки у введеній ціні:");
          priceErrors.forEach(error -> System.out.println("- " + error));
        }
      } catch (NumberFormatException e) {
        System.out.println("Ціна має бути числом. Спробуйте знову.");
      }
    }

    while (true) {
      System.out.print("Введіть новий розмір одиниці (наприклад, для блоків): ");
      String newUnitSize = scanner.nextLine();
      try {
        material.setUnitSize(Double.parseDouble(newUnitSize));

        List<String> sizeErrors = MaterialValidator.validate(material);
        if (sizeErrors.isEmpty()) {
          break;
        } else {
          System.out.println("Знайдено помилки у введеному розмірі одиниці:");
          sizeErrors.forEach(error -> System.out.println("- " + error));
        }
      } catch (NumberFormatException e) {
        System.out.println("Розмір одиниці має бути числом. Спробуйте знову.");
      }
    }

    // Остаточна валідація всього матеріалу перед збереженням
    List<String> finalErrors = MaterialValidator.validate(material);
    if (!finalErrors.isEmpty()) {
      System.out.println("Помилки при збереженні матеріалу:");
      finalErrors.forEach(error -> System.out.println("- " + error));
      return; // Якщо є помилки, припиняємо збереження
    }

    // Збереження оновлень
    FileUtil.saveToFile(MATERIALS_FILE_PATH, materials);
    System.out.println("Матеріал успішно оновлено.");
  }

  // Метод редагування категорії паркування
  public static void editCategory() {
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);

    if (categories.isEmpty()) {
      System.out.println("Список категорій порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    Category category = null;

    // Поки користувач не введе правильний ID
    while (category == null) {
      System.out.print("Введіть ID категорії для редагування: ");
      String inputId = scanner.nextLine();

      if (!UserValidator.isValidUUID(inputId)) {
        System.out.println("Некоректний формат ID. Спробуйте знову.");
        continue;  // Запитуємо ID знову
      }

      UUID categoryId = UUID.fromString(inputId);
      category = categories.stream()
          .filter(c -> c.getId().equals(categoryId))
          .findFirst()
          .orElse(null);

      if (category == null) {
        System.out.println("Категорія з таким ID не знайдена. Спробуйте ще раз.");
      }
    }

    // Редагуємо назву категорії
    String newName;
    while (true) {
      System.out.print("Введіть нову назву категорії: ");
      newName = scanner.nextLine();

      if (CategoryValidator.isValidCategoryName(newName)) {
        category.setName(newName);
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректна назва категорії. Спробуйте знову.");
      }
    }

    FileUtil.saveToFile(CATEGORIES_FILE_PATH, categories);
    System.out.println("Категорію успішно оновлено.");
  }
}
