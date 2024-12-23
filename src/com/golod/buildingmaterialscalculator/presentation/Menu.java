package com.golod.buildingmaterialscalculator.presentation;

import com.golod.buildingmaterialscalculator.domain.model.Material;
import com.golod.buildingmaterialscalculator.service.CalculationService;
import com.golod.buildingmaterialscalculator.service.CategoryService;
import com.golod.buildingmaterialscalculator.service.MaterialService;
import com.golod.buildingmaterialscalculator.service.operations.AuthorizationService;
import com.golod.buildingmaterialscalculator.service.operations.DeleteService;
import com.golod.buildingmaterialscalculator.service.operations.EditService;
import com.golod.buildingmaterialscalculator.service.operations.RegistrationService;
import com.golod.buildingmaterialscalculator.service.operations.SearchService;
import com.golod.buildingmaterialscalculator.service.validation.UserInputHandler;
import java.util.List;

public class Menu {

  public Menu() {
  }

  public static void show() throws IllegalAccessException {
    UserInputHandler userInputHandler = new UserInputHandler();
    SearchService searchService = new SearchService();  // Ініціалізація SearchService
    while (true) {
      if (Application.currentUser == null) {
        System.out.println("=== Прогама для розрахунку кількості матеріалів для будівництва ===");
        System.out.println("1) Реєстрація");
        System.out.println("2) Авторизація");
        System.out.println("0) Вихід");

        int choice = userInputHandler.getIntInput("Ваш вибір: ");

        switch (choice) {
          case 1:
            RegistrationService.registration();
            AuthorizationService.authorization();
            break;
          case 2:
            AuthorizationService.authorization();
            break;
          case 0:
            System.out.println("Дякую за використання.");
            System.exit(0);
            break;
          default:
            System.out.println("Невірний вибір. Спробуйте ще раз.");
            break;
        }
        continue; // Повернення на початок циклу
      }

      String userRole = Application.currentUser.getRole();

      if ("".equals(userRole)) {
        System.out.println("1) Реєстрація");
        System.out.println("2) Авторизація");
      } else {
        String art =
            "      `'::::.\n" +
                "        _____A_\n" +
                "       /      /\\\n" +
                "    __/__ /\\__/  \\___\n" +
                "---/__|\" '' \"| /___/\\----\n" +
                "   |''|\"'||'\"| |' '||\n" +
                "   `\"\"`\"\"))\"\"`";
        System.out.println(art);
        System.out.println("1) Перегляд даних");
        System.out.println("2) Розрахунок необхідної кількості матеріалів");
        System.out.println("3) Пошук даних");

        if ("Admin".equals(userRole)) {
          System.out.println("4) Додавання даних");
          System.out.println("5) Редагування даних");
          System.out.println("6) Видалення даних");
        }
      }

      System.out.println("0) Вихід з головного меню");

      int choice = userInputHandler.getIntInput("Ваш вибір: ");

      switch (choice) {
        case 1:
          showViewMenu();
          break;
        case 2:
          showCalculationMenu();
          break;
        case 3:
          showSearchMenu(searchService);
          break;
        case 4:
          if ("Admin".equals(userRole)) {
            showAddMenu();
          }
          break;
        case 5:
          if ("Admin".equals(userRole)) {
            showEditMenu();
          }
          break;
        case 6:
          if ("Admin".equals(userRole)) {
            showDeleteMenu();
          }
          break;
        case 0:
          Application.currentUser = null; // Вихід з головного меню
          break;
        default:
          System.out.println("Невірний вибір. Спробуйте ще раз.");
          break;
      }
    }
  }

  private static void showSearchMenu(SearchService searchService) {
    System.out.println("1) Пошук матеріалів");
    System.out.println("2) Пошук категорій");
    System.out.println("3) Повернутися до головного меню");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        String materialName = new UserInputHandler().getStringInput("Введіть назву матеріалу: ");
        searchService.searchMaterialsByName(materialName);
        break;
      case 2:
        String categoryName = new UserInputHandler().getStringInput("Введіть назву категорії: ");
        searchService.searchCategoryByName(categoryName);
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showViewMenu() {
    System.out.println("1) Переглянути матеріали");
    System.out.println("2) Переглянути категорії");
    System.out.println("3) Повернутися до головного меню");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        MaterialService.main(new String[]{});
        break;
      case 2:
        CategoryService.main(new String[]{});
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }
  public static void showCalculationMenu() {
    while (true) {
      System.out.println("1) Розрахунок кількості цементної суміші");
      System.out.println("2) Розрахунок кількості матеріалів для стін");
      System.out.println("3) Розрахунок кількості покрівельного матеріалу");
      System.out.println("4) Розрахунок кількості підлогового покриття");
      System.out.println("5) Розрахунок кількості штукатурки для стін");
      System.out.println("6) Повернутися до головного меню");
      System.out.print("Оберіть пункт меню: ");

      int choice = new UserInputHandler().getIntInput("Ваш вибір: ");
      CalculationService calculationService = new CalculationService();
      switch (choice) {
        case 1:
          calculationService.calculateCement();
          break;
        case 2:
          calculationService.calculateWallMaterials();
          break;
        case 3:
          calculationService.calculateRoofingMaterial();
          break;
        case 4:
          calculationService.calculateFlooring();
          break;
        case 5:
          calculationService.calculatePlaster();
          break;
        case 6:
          return; // Вихід до головного меню
        default:
          System.out.println("Невірний вибір. Спробуйте ще раз.");
      }
    }
  }

  private static void showAddMenu() {
    System.out.println("1) Додати новий матеріал");
    System.out.println("2) Додати нову категорію ");
    System.out.println("3) Повернутися до головного меню");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        MaterialService.addMaterial();
        break;
      case 2:
        CategoryService.addCategory();
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showEditMenu() {
    System.out.println("Що ви хочете редагувати?");
    System.out.println("1) Редагувати матеріал");
    System.out.println("2) Редагувати категорію");
    System.out.println("3) Повернутися до головного меню");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        EditService.editMaterial();
        break;
      case 2:
        EditService.editCategory();
        break;
      case 3:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }

  private static void showDeleteMenu() {
    System.out.println("Що ви хочете видалити?");
    System.out.println("1) Видалити матеріал");
    System.out.println("2) Видалити користувача");
    System.out.println("3) Видалити категорію");
    System.out.println("4) Повернутися до головного меню");

    int choice = new UserInputHandler().getIntInput("Ваш вибір: ");

    switch (choice) {
      case 1:
        DeleteService.deleteMaterial();
        break;
      case 2:
        DeleteService.deleteUser();
        break;
      case 3:
        DeleteService.deleteCategory();
        break;
      case 4:
        return;
      default:
        System.out.println("Невірний вибір. Спробуйте ще раз.");
        break;
    }
  }
}
