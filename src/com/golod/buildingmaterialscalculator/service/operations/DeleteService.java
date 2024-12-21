package com.golod.buildingmaterialscalculator.service.operations;

import com.golod.buildingmaterialscalculator.domain.model.Category;
import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.domain.model.User;
import com.golod.buildingmaterialscalculator.service.util.FileUtil;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;
import com.golod.buildingmaterialscalculator.service.validation.UserInputHandler;

import java.util.List;

public class DeleteService {

  private static final String CATEGORY_FILE_PATH = "data/categories.json";
  private static final String MATERIAL_FILE_PATH = "data/materials.json";
  private static final String USER_FILE_PATH = "data/users.json";

  public static void deleteCategory() {
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORY_FILE_PATH, Category[].class);

    if (categories.isEmpty()) {
      System.out.println("Список категорій порожній.");
      return;
    }

    System.out.println("Список доступних категорій:");
    for (Category category : categories) {
      System.out.println("ID категорії: " + category.getId() + ", Назва: " + category.getName());
    }

    String categoryId = UserInputHandler.getStringInput("Введіть ID категорії, яку хочете видалити:");

    Category selectedCategory = categories.stream()
        .filter(category -> category.getId().toString().equals(categoryId))
        .findFirst()
        .orElse(null);

    if (selectedCategory != null) {
      categories.remove(selectedCategory);
      FileUtil.saveToFile(CATEGORY_FILE_PATH, categories);
      System.out.println("Категорію успішно видалено.");
    } else {
      System.out.println("Категорію з введеним ID не знайдено.");
    }
  }

  public static void deleteMaterial() {
    List<Material> materials = JsonDataReader.modelDataJsonReader(MATERIAL_FILE_PATH, Material[].class);

    if (materials.isEmpty()) {
      System.out.println("Список матеріалів порожній.");
      return;
    }

    System.out.println("Список доступних матеріалів:");
    for (Material material : materials) {
      System.out.println("ID матеріалу: " + material.getId() + ", Назва: " + material.getName());
    }

    String materialId = UserInputHandler.getStringInput("Введіть ID матеріалу, який хочете видалити:");

    Material selectedMaterial = materials.stream()
        .filter(material -> material.getId().toString().equals(materialId))
        .findFirst()
        .orElse(null);

    if (selectedMaterial != null) {
      materials.remove(selectedMaterial);
      FileUtil.saveToFile(MATERIAL_FILE_PATH, materials);
      System.out.println("Матеріал успішно видалено.");
    } else {
      System.out.println("Матеріал з введеним ID не знайдено.");
    }
  }

  public static void deleteUser() {
    List<User> users = JsonDataReader.modelDataJsonReader(USER_FILE_PATH, User[].class);

    if (users.isEmpty()) {
      System.out.println("Список користувачів порожній.");
      return;
    }

    System.out.println("Список користувачів:");
    for (User user : users) {
      System.out.println("ID користувача: " + user.getUserId() + ", Ім'я: " + user.getUsername());
    }

    String userId = UserInputHandler.getStringInput("Введіть ID користувача для видалення:");

    User selectedUser = users.stream()
        .filter(user -> user.getUserId().toString().equals(userId))
        .findFirst()
        .orElse(null);

    if (selectedUser != null) {
      users.remove(selectedUser);
      FileUtil.saveToFile(USER_FILE_PATH, users);
      System.out.println("Користувача успішно видалено.");
    } else {
      System.out.println("Користувача з таким ID не знайдено.");
    }
  }
}
