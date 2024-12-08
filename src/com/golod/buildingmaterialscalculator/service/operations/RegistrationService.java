package com.golod.buildingmaterialscalculator.service.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golod.buildingmaterialscalculator.domain.model.User;
import com.golod.buildingmaterialscalculator.presentation.Application;
import com.golod.buildingmaterialscalculator.service.util.JsonDataReader;
import com.golod.buildingmaterialscalculator.service.validation.UserValidator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.mindrot.bcrypt.BCrypt;

public class RegistrationService {

  private static final String USERS_FILE_PATH = "data/users.json";
  private static final Scanner scanner = new Scanner(System.in);
  private static final UserValidator validationService = new UserValidator();

  // Метод для реєстрації користувача
  public static void registration() {
    List<User> users = JsonDataReader.modelDataJsonReader(USERS_FILE_PATH, User[].class);

    String username = getUsername(users);
    String password = getPassword();
    String hashedPassword = hashPassword(password);  // Хешуємо пароль
    String email = getEmail();

    String role = "User"; // роль "User" за замовчуванням
    UUID newUserId = getNewUserId(users);

    User newUser = new User(newUserId, username, hashedPassword, email, role);  // Використовуємо хешований пароль
    Application.currentUser = newUser;

    users.add(newUser);
    saveUsersToJson(users, USERS_FILE_PATH);

    System.out.println("Реєстрація пройшла успішно.");

    navigateToAuthorization();
  }

  // Метод для отримання унікального логіну
  private static String getUsername(List<User> users) {
    String username;
    do {
      System.out.println("Введіть логін:");
      username = scanner.nextLine().trim();

      if (!validationService.isNotEmpty(username)) {
        System.out.println("Логін не може бути порожнім.");
      } else if (!validationService.isValidName(username)) {
        System.out.println("Логін повинен містити хоча б один буквенний символ.");
      } else if (!validationService.isLoginUnique(users, username)) {
        System.out.println("Цей логін вже використовується. Виберіть інший.");
      } else {
        break;
      }
    } while (true);
    return username;
  }

  // Метод для отримання паролю
  private static String getPassword() {
    String password;
    do {
      System.out.println("Введіть пароль:");
      password = scanner.nextLine().trim();

      if (!validationService.isValidPassword(password)) {
        System.out.println("Пароль має містити одну велику та маленьку букву, мінімум 8 символів.");
      }
    } while (!validationService.isValidPassword(password));
    return password;
  }

  // Метод для отримання електронної пошти
  private static String getEmail() {
    String email;
    do {
      System.out.println("Введіть email:");
      email = scanner.nextLine().trim();

      if (!validationService.isValidEmail(email)) {
        System.out.println("Невірний формат електронної пошти.");
      }
    } while (!validationService.isValidEmail(email));
    return email;
  }

  // Метод для отримання нового UUID користувача
  private static UUID getNewUserId(List<User> users) {
    // Генеруємо новий унікальний UUID
    return UUID.randomUUID();
  }

  // Метод для хешування паролю
  private static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());  // Хешуємо пароль
  }

  // Метод для збереження користувачів у файл
  private static void saveUsersToJson(List<User> users, String filePath) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      File file = new File(filePath);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    } catch (IOException e) {
      System.err.println("Помилка при збереженні користувачів: " + e.getMessage());
    }
  }

  // Метод для навігації до головного меню
  private static void navigateToAuthorization() {
    AuthorizationService.authorization();
  }
}
