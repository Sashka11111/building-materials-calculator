package com.golod.buildingmaterialscalculator.service.operations;

import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SearchService {

  private static final String CATEGORY_FILE_PATH = "data/categories.json";
  private static final String MATERIAL_FILE_PATH = "data/materials.json";

  // Пошук матеріалів за назвою категорії
  public List<Material> searchMaterialsByCategoryName(String categoryName) {
    List<Category> categories = loadCategories();

    Category category = findCategoryByName(categoryName, categories);
    if (category == null) {
      System.out.println("Категорія не знайдена!");
      return List.of();
    }

    List<Material> materials = loadMaterials();
    List<Material> result = filterMaterialsByCategory(materials, category.getId());

    displayMaterialsResult(result, categoryName);
    return result;
  }

  // Пошук матеріалів за ID
  public List<Material> searchMaterialsById(UUID materialId) {
    List<Material> materials = loadMaterials();

    List<Material> result = materials.stream()
        .filter(material -> material.getId().equals(materialId))
        .collect(Collectors.toList());

    displayMaterialsResult(result, materialId.toString());
    return result;
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

  // Фільтрація матеріалів за ID
  private List<Material> filterMaterialsByCategory(List<Material> materials, UUID categoryId) {
    return materials.stream()
        .filter(material -> material.getCategory().getId().equals(categoryId)) // Порівнюємо ID категорії
        .collect(Collectors.toList());
  }

  // Виведення результатів пошуку
  private void displayMaterialsResult(List<Material> result, String searchCriteria) {
    if (result.isEmpty()) {
      System.out.println("Матеріали для '" + searchCriteria + "' не знайдені.");
    } else {
      result.forEach(material -> {
        System.out.println("Матеріал: " + material.getName() + ", Категорія: " + material.getCategory().getName());
      });
    }
  }
}
