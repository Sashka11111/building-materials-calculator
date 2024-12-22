package com.golod.buildingmaterialscalculator.service.validation;

import java.util.Scanner;

public class UserInputHandler {
  private static Scanner scanner = new Scanner(System.in);

  // Введення рядка
  public static String getStringInput(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine();
  }

  // Введення цілого числа
  public static int getIntInput(String prompt) {
    int input = -1;
    boolean valid = false;
    while (!valid) {
      try {
        System.out.println(prompt);
        input = Integer.parseInt(scanner.nextLine()); // Читаємо введене число як рядок
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Введено некоректне число. Спробуйте ще раз.");
      }
    }
    return input;
  }

  // Введення дійсного числа
  public static double getDoubleInput(String prompt) {
    double input = -1;
    boolean valid = false;
    while (!valid) {
      try {
        System.out.println(prompt);
        input = Double.parseDouble(scanner.nextLine()); // Читаємо введене число як рядок
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Введено некоректне число. Будь ласка, введіть дійсне число.");
      }
    }
    return input;
  }
}
