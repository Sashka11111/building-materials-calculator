package com.golod.buildingmaterialscalculator.service.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golod.buildingmaterialscalculator.domain.model.User;
import com.golod.buildingmaterialscalculator.presentation.Application;
import com.golod.buildingmaterialscalculator.presentation.Menu;
import com.golod.buildingmaterialscalculator.service.validation.UserInputHandler;
import com.golod.buildingmaterialscalculator.service.validation.UserValidator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.mindrot.bcrypt.BCrypt;

public class AuthorizationService {

  private static final String USERS_FILE_PATH = "data/users.json";
  private static final UserInputHandler inputHandler = new UserInputHandler();

  public static void authorization() {
    String role = getRoleFromUser();
    String username = getValidatedUsernameFromUser();
    String password = getValidatedPasswordFromUser();

    List<User> users = readUsersFromJson(USERS_FILE_PATH);

    if (users.isEmpty()) {
      System.out.println("Список користувачів порожній. Зверніться до адміністратора.");
      return;
    }

    User user = findUserByUsername(users, username);

    if (user == null) {
      System.out.println("Користувача з таким логіном не існує. Спробуйте ще раз.");
      return;
    }

    if (!user.getRole().equalsIgnoreCase(role)) {
      System.out.println("Роль не відповідає вказаному логіну. Спробуйте ще раз.");
      return;
    }

    if (!BCrypt.checkpw(password, user.getPassword())) {  // Перевірка хешованого пароля
      System.out.println("Неправильний пароль. Спробуйте ще раз.");
      return;
    }

    Application.currentUser = user;
    System.out.println("Авторизація пройшла успішно.");
    navigateToMenu();
  }

  private static String getRoleFromUser() {
    String role;
    while (true) {
      System.out.println("Оберіть роль:");
      System.out.println("1) Admin");
      System.out.println("2) User");

      int roleChoice = inputHandler.getIntInput("Ваш вибір: ");

      if (roleChoice == 1) {
        role = "Admin";
        break;
      } else if (roleChoice == 2) {
        role = "User";
        break;
      } else {
        System.out.println("Невірний вибір ролі. Спробуйте ще раз.");
      }
    }
    return role;
  }

  private static String getValidatedUsernameFromUser() {
    String username;
    while (true) {
      username = inputHandler.getStringInput("Введіть логін:");
      if (UserValidator.isNotEmpty(username)) {
        break;
      } else {
        System.out.println("Логін не може бути порожнім. Спробуйте ще раз.");
      }
    }
    return username;
  }

  private static String getValidatedPasswordFromUser() {
    String password;
    while (true) {
      password = inputHandler.getStringInput("Введіть пароль:");
      if (UserValidator.isValidPassword(password)) {
        break;
      } else {
        System.out.println("Пароль повинен містити хоча б одну велику літеру, одну маленьку літеру, цифру та бути не менше 8 символів. Спробуйте ще раз.");
      }
    }
    return password;
  }

  private static User findUserByUsername(List<User> users, String username) {
    return users.stream()
        .filter(user -> user.getUsername().equalsIgnoreCase(username))
        .findFirst()
        .orElse(null);
  }

  private static List<User> readUsersFromJson(String filePath) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return Arrays.asList(objectMapper.readValue(new File(filePath), User[].class));
    } catch (IOException e) {
      System.err.println("Помилка при читанні користувачів з файлу: " + e.getMessage());
      return List.of();
    }
  }

  private static void navigateToMenu() {
    try {
      Menu.show();
    } catch (IllegalAccessException e) {
      System.err.println("Помилка при відкритті меню: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
