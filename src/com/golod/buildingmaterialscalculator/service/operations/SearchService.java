package com.golod.buildingmaterialscalculator.service.operations;

import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

  private static final String CATEGORY_FILE_PATH = "data/categories.json";
  private static final String MATERIAL_FILE_PATH = "data/materials.json";

  // Пошук матеріалів за назвою
  public List<Material> searchMaterialsByName(String materialName) {
    List<Material> materials = loadMaterials();

    List<Material> result = materials.stream()
        .filter(material -> material.getName().equalsIgnoreCase(materialName))
        .collect(Collectors.toList());

    displayMaterialsResult(result, materialName);
    return result;
  }

// Пошук категорії за назвою
  public Category searchCategoryByName(String categoryName) {
    List<Category> categories = loadCategories();

    Category category = findCategoryByName(categoryName, categories);
    if (category == null) {
      System.out.println("Категорія не знайдена!");
    } else {
      // Виведення знайденої категорії
      System.out.println("ID категорії: " + category.getId());
      System.out.println("Назва категорії: " + category.getName());
      System.out.println("==========================================");
    }
    return category;
  }


  // Завантаження категорій з JSON
  private List<Category> loadCategories() {
    return JsonDataReader.modelDataJsonReader(CATEGORY_FILE_PATH, Category[].class);
  }

  // Завантаження матеріалів з JSON
  private List<Material> loadMaterials() {
    return JsonDataReader.modelDataJsonReader(MATERIAL_FILE_PATH, Material[].class);
  }

  // Пошук категорії за назвою
  private Category findCategoryByName(String categoryName, List<Category> categories) {
    return categories.stream()
        .filter(c -> c.getName().equalsIgnoreCase(categoryName))
        .findFirst()
        .orElse(null);
  }

  // Виведення результатів пошуку
// Виведення результатів пошуку
  private void displayMaterialsResult(List<Material> result, String searchCriteria) {
    if (result.isEmpty()) {
      System.out.println("Матеріали для '" + searchCriteria + "' не знайдені.");
    } else {
      result.forEach(material -> {
        System.out.println("ID: " + material.getId());
        System.out.println("Матеріал: " + material.getName());
        System.out.println("Категорія: " + material.getCategory().getName()); // Назва категорії
        System.out.println("Одиниця виміру: " + material.getUnit()); // Одиниця виміру (наприклад, кг, шт)
        System.out.println("Ціна за одиницю: " + material.getUnitPrice()); // Ціна за одиницю
        System.out.println("Розмір одиниці: " + material.getUnitSize()); // Розмір одиниці (наприклад, для блоків)
        System.out.println("==========================================");
      });
    }
  }
}
