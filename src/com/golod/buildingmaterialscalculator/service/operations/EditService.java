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
    // Завантажуємо список матеріалів
    List<Material> materials = JsonDataReader.modelDataJsonReader(MATERIALS_FILE_PATH, Material[].class);

    if (materials.isEmpty()) {
      System.out.println("Список матеріалів порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    Material material = null;

    // Поки користувач не введе правильний ID
    while (material == null) {
      System.out.print("Введіть ID матеріалу для редагування: ");
      String inputId = scanner.nextLine();

      if (!UserValidator.isValidUUID(inputId)) {
        System.out.println("Некоректний формат ID. Спробуйте знову.");
        continue;  // Запитуємо ID знову
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

    // Редагуємо назву матеріалу
    String newMaterialName;
    while (true) {
      System.out.print("Введіть нову назву матеріалу: ");
      newMaterialName = scanner.nextLine();

      // Перевіряємо лише назву матеріалу
      if (newMaterialName != null && !newMaterialName.isEmpty()) {
        material.setName(newMaterialName);
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректна назва матеріалу. Спробуйте знову.");
      }
    }


    // Редагуємо одиницю виміру
    String newUnit;
    while (true) {
      System.out.print("Введіть нову одиницю виміру (наприклад, 'кг', 'м2', 'шт'): ");
      newUnit = scanner.nextLine();

      // Перевірка, чи одиниця виміру не порожня
      if (newUnit != null && !newUnit.isEmpty()) {
        material.setUnit(newUnit);
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Одиниця виміру не може бути порожньою. Спробуйте знову.");
      }
    }


    // Редагуємо ціну за одиницю
    String newUnitPrice;
    while (true) {
      System.out.print("Введіть нову ціну матеріалу: ");
      newUnitPrice = scanner.nextLine();

      if (UserValidator.isValidNumber(newUnitPrice)) {
        material.setUnitPrice(Double.parseDouble(newUnitPrice));
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректна ціна матеріалу. Спробуйте знову.");
      }
    }

    // Редагуємо розмір одиниці
    String newUnitSize;
    while (true) {
      System.out.print("Введіть новий розмір одиниці (наприклад, для блоків): ");
      newUnitSize = scanner.nextLine();

      if (UserValidator.isValidNumber(newUnitSize)) {
        material.setUnitSize(Double.parseDouble(newUnitSize));
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректний розмір одиниці. Спробуйте знову.");
      }
    }
// Редагуємо категорію
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
    String newCategoryName;
    while (true) {
      System.out.print("Введіть нову категорію: ");
      newCategoryName = scanner.nextLine();

      if (CategoryValidator.isValidCategoryName(newCategoryName)) {
        // Шукаємо категорію за допомогою звичайного циклу замість лямбда-виразу
        Category category = null;
        for (Category c : categories) {
          if (c.getName().equalsIgnoreCase(newCategoryName)) {
            category = c;
            break;
          }
        }

        if (category != null) {
          material.setCategory(category);
          break;  // Виходимо з циклу, якщо категорія знайдена
        } else {
          System.out.println("Категорія не знайдена. Спробуйте ще раз.");
        }
      } else {
        System.out.println("Некоректна категорія. Спробуйте знову.");
      }
    }

    // Перевірка на помилки
    List<String> materialErrors = MaterialValidator.validate(material);
    if (!materialErrors.isEmpty()) {
      System.out.println("Помилки в матеріалі:");
      materialErrors.forEach(System.out::println);
      return; // Виходимо, якщо є помилки
    }

    // Зберігаємо оновлений список матеріалів у файл
    FileUtil.saveToFile(MATERIALS_FILE_PATH, materials);
    System.out.println("Матеріал успішно оновлено.");
  }
}
